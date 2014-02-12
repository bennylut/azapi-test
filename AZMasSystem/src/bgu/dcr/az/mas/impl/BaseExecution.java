/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.mas.impl;

import bgu.dcr.az.api.exen.ExecutionResult;
import bgu.dcr.az.execs.ThreadSafeProcTable;
import bgu.dcr.az.execs.api.Scheduler;
import bgu.dcr.az.execs.api.TerminationReason;
import bgu.dcr.az.mas.AgentController;
import bgu.dcr.az.mas.AgentDistributer;
import bgu.dcr.az.mas.AgentSpawner;
import bgu.dcr.az.mas.Execution;
import bgu.dcr.az.mas.ExecutionService;
import bgu.dcr.az.mas.UnmetRequirementException;
import bgu.dcr.az.mas.exp.ExperimentExecutionException;
import bgu.dcr.az.mas.MessageRouter;
import bgu.dcr.az.mas.ExecutionEnvironment;
import bgu.dcr.az.mas.exp.Experiment;
import bgu.dcr.az.mas.impl.stat.StatisticalInfoStreamProc;
import bgu.dcr.az.mas.stat.StatisticalInfoStream;
import bgu.dcr.az.mas.stat.data.ExecutionInitializationInfo;
import bgu.dcr.az.mas.stat.data.ExecutionTerminationInfo;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author User
 * @param <T>
 */
public abstract class BaseExecution<T extends HasSolution> implements Execution<T> {

    private final Map<Class<? extends ExecutionService>, ExecutionServiceWithInitializationData> services = new HashMap<>();

    private final ExecutionEnvironment env;
    private final T data;
    private final Experiment containingExperiment;
    private StatisticalInfoStreamProc statisticalStream;

    public BaseExecution(T data, Experiment containingExperiment, AgentDistributer distributer, AgentSpawner spawner, ExecutionEnvironment env) {
        this.env = env;
        this.data = data;
        this.containingExperiment = containingExperiment;

        supply(AgentDistributer.class, distributer);
        supply(AgentSpawner.class, spawner);
    }

    @Override
    public ExecutionResult execute(Scheduler sched, int numCores) throws ExperimentExecutionException, InterruptedException {
        ThreadSafeProcTable table = new ThreadSafeProcTable();
        table.add(statisticalStream = new StatisticalInfoStreamProc(-1));
        supply(MessageRouter.class, new BaseMessageRouter());

        try {

            initialize();

            //initialize rest of services
            for (ExecutionServiceWithInitializationData service : services.values()) {
                service.initialize();
            }

            //create agentcontrollers
            Collection<AgentController> controllers = createControllers();
            for (AgentController c : controllers) {
                table.add(c);
            }
        } catch (InitializationException ex) {
            throw new ExperimentExecutionException("error on experiment initialization, see cause", ex);
        }

        statisticalStream.writeNow(new ExecutionInitializationInfo(numCores));

        TerminationReason terminationResult = sched.schedule(table, numCores);

        ExecutionResult result = new ExecutionResult<>();
        if (terminationResult.isError()) {
            result.toCrushState(terminationResult.getErrorDescription());
        } else {
            result.toSucceefulState(data().solution());
        }

        statisticalStream.writeNow(new ExecutionTerminationInfo(result));

        return result;
    }

    @Override
    public <T extends ExecutionService> T require(Class<T> service) throws InitializationException {
        ExecutionServiceWithInitializationData result = services.get(service);

        if (result == null) {
            throw new UnmetRequirementException("requrement for " + service.getCanonicalName() + " is unmet");
        }

        result.initialize();
        return (T) result.service;
    }

    @Override
    public final void supply(Class<? extends ExecutionService> serviceKey, ExecutionService service) {
        services.put(serviceKey, new ExecutionServiceWithInitializationData(service));
    }

    @Override
    public boolean hasRequirement(Class<? extends ExecutionService> service) {
        return services.containsKey(service);
    }

    protected abstract Collection<AgentController> createControllers() throws InitializationException;

    protected abstract void initialize() throws InitializationException;

    @Override
    public ExecutionEnvironment getEnvironment() {
        return env;
    }

    private enum InitializatinState {

        INITIALIZED, INITIALIZING, UNINITIALIZED;
    }

    @Override
    public T data() {
        return data;
    }

    private class ExecutionServiceWithInitializationData {

        ExecutionService service;
        InitializatinState initializatinState;

        public ExecutionServiceWithInitializationData(ExecutionService service) {
            this.service = service;
            this.initializatinState = InitializatinState.UNINITIALIZED;
        }

        public void initialize() throws InitializationException {
            switch (initializatinState) {
                case INITIALIZED:
                    break;
                case INITIALIZING:
                    throw new InitializationException("circular dependency found: " + collectInitializingServices());
                case UNINITIALIZED:
                    this.initializatinState = InitializatinState.INITIALIZING;
                    this.service.initialize(BaseExecution.this);
                    this.initializatinState = InitializatinState.INITIALIZED;
                    break;
                default:
                    throw new AssertionError(initializatinState.name());
            }
        }

        private String collectInitializingServices() {
            StringBuilder sb = new StringBuilder();
            for (ExecutionServiceWithInitializationData s : services.values()) {
                if (s.initializatinState == InitializatinState.INITIALIZING) {
                    sb.append("[").append(s.service.getClass().getSimpleName()).append("]");
                }
            }

            return sb.toString();
        }
    }

    @Override
    public Experiment getContainingExperiment() {
        return this.containingExperiment;
    }

    @Override
    public StatisticalInfoStream informationStream() {
        return statisticalStream;
    }

}
