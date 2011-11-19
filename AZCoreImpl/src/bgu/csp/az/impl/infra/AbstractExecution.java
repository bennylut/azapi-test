package bgu.csp.az.impl.infra;

import bgu.csp.az.impl.async.AsyncExecution;
import bgu.csp.az.api.Agent;
import bgu.csp.az.api.Agent.PlatformOps;
import bgu.csp.az.api.AgentRunner;
import bgu.csp.az.impl.AlgorithmMetadata;
import bgu.csp.az.api.Mailer;
import bgu.csp.az.api.pgen.Problem;
import bgu.csp.az.api.infra.stat.Statistic;
import bgu.csp.az.api.infra.Execution;
import bgu.csp.az.api.infra.ExecutionResult;
import bgu.csp.az.api.SystemClock;
import bgu.csp.az.api.tools.Assignment;
import bgu.csp.az.api.tools.IdleDetector;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the object that represents the run time environment for a single algorithm execution, can be reused with the use of 
 * the reset function
 * There can be several of those for example: one especially designed for testing,
 * one for a distributed environment and one for using in UI’s etc.
 * this way the same algorithm can run without changes in every environment that we choose
 * – with this objects you control the running of the experiment / project / algorithm, 
 * get notifications about interesting thing that happened there, let you shut down an execution etc.
 * @author bennyl
 */
public abstract class AbstractExecution extends AbstractProcess implements Execution {

    private Statistic statTree; //will get constracted when the global problem is setted.
    private Problem problem;
    private Mailer mailer;
    private boolean shuttingdown; //this variable is used to check that the execution is doing the process of shuting down only once.
    private ExecutionResult result = new ExecutionResult();
    private ExecutionResult partialResult = new ExecutionResult();
    private Map<String, Object> parameterValues;
    private AlgorithmMetadata algorithmMetadata;
    private IdleDetector idleDetector;
    private ExecutorService executorService;
    private AgentRunner[] agentRunners;
    private Agent[] agents;
    private LinkedList<LogListener> logListeners = new LinkedList<LogListener>();
    private SystemClock clock;

    /**
     * 
     */
    public AbstractExecution(ExecutorService exec, Problem p, Mailer m, AlgorithmMetadata a) {
        this.parameterValues = new HashMap<String, Object>();
        this.shuttingdown = false;
        this.parameterValues.clear();
        this.executorService = exec;
        this.mailer = m;
        this.problem = p;
        this.statTree = new Statistic(p.getMetadata());
        this.algorithmMetadata = a;
    }

    /**
     * will stop the current execution and set the result to no solution
     * TODO: maybe keep track of the execution status via enum (working, done, crushed, etc.)
     * @param ex
     * @param error
     */
    @Override
    public void reportCrushAndStop(Exception ex, String error) {
        if (!shuttingdown) {
            setResult(new ExecutionResult(ex));
            shuttingdown = true;
            stop();

            System.out.println("PANIC! " + ex.getMessage() + ", [USER TEXT]: " + error);
        }
    }

    @Override
    public SystemClock getSystemClock() {
        return this.clock;
    }

    @Override
    public int getNumberOfAgentRunners() {
        return agentRunners.length;
    }
    
    public void setSystemClock(SystemClock clock) {
        this.clock = clock;
    }

    public void addLogListener(LogListener ll) {
        this.logListeners.add(ll);
    }

    public void removeLogListener(LogListener ll) {
        this.logListeners.remove(ll);
    }

    @Override
    public AgentRunner getAgentRunnerFor(Agent a) {
        return agentRunners[a.getId()];
    }

    protected boolean generateAgents() {
        try {
            agents = new Agent[getGlobalProblem().getNumberOfVariables()];
            for (int i = 0; i < agents.length; i++) {
                getAgents()[i] = getAlgorithm().getAgentClass().newInstance();
                PlatformOps apops = Agent.PlatformOperationsExtractor.extract(getAgents()[i]);
                apops.setExecution(this);
                apops.setId(i);

            }
            return true;
        } catch (InstantiationException ex) {
            Logger.getLogger(AsyncExecution.class.getName()).log(Level.SEVERE, "every agent must have empty constractor", ex);
            reportCrushAndStop(ex, "execution failed on initial state - every agent must have empty constractor");
            return false;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AsyncExecution.class.getName()).log(Level.SEVERE, "agent cannot be abstract/ cannot have a private constractor", ex);
            reportCrushAndStop(ex, "execution failed on initial state - agent cannot be abstract/ cannot have a private constractor");
            return false;
        }
    }

    @Override
    public void stop() {
        executorService.shutdownNow();
    }

    protected ExecutorService getExecutorService() {
        return executorService;
    }

    protected void setAgentRunners(AgentRunner[] runners) {
        this.agentRunners = runners;
    }

    protected Agent[] getAgents() {
        return agents;
    }

    protected AgentRunner[] getRunners() {
        return agentRunners;
    }

    @Override
    public void reportFinalAssignment(Assignment answer) {
        result = new ExecutionResult(answer);
    }

    public IdleDetector getIdleDetector() {
        return idleDetector;
    }

    public void setIdleDetector(IdleDetector idet) {
        this.idleDetector = idet;
    }

    /**
     * @return the statistics tree - a tree contains statistics about this execution
     */
    @Override
    public Statistic getStatisticsTree() {
        return statTree;
    }

    /**
     * @return the global problem - 
     * each agent have its own "version" of problem that is based on the global problem 
     * using the global problem is reserved to the execution environment or to the execution tools - do not use it inside 
     * your algorithms - use Agents getProblem() instaed.
     */
    @Override
    public Problem getGlobalProblem() {
        return this.problem;
    }

    /**
     * @return the mailer attached to this execution.
     */
    @Override
    public Mailer getMailer() {
        return this.mailer;
    }

    /**
     * cause the executed environment to log the given data
     * this implementation only print the data into the screen
     * @param agent
     * @param data
     */
    @Override
    public void log(int agent, String mailGroupKey, String data) {
        for (LogListener ll : logListeners) {
            ll.onLog(agent, mailGroupKey, data);
        }
    }

    protected void setResult(ExecutionResult result) {
        this.result = result;
    }

    @Override
    public ExecutionResult getResult() {
        return result;
    }

    public AlgorithmMetadata getAlgorithm() {
        return algorithmMetadata;
    }

    /**
     * ugly synchronization - replace with semaphore..
     * @param var
     * @param val 
     */
    @Override
    public synchronized void reportPartialAssignment(int var, int val) {
        if (partialResult.getAssignment() == null) {
            partialResult = new ExecutionResult(new Assignment());
        }
        partialResult.getAssignment().assign(var, val);
    }

    @Override
    public Object getParameterValue(String name) {
        return parameterValues.get(name);
    }

    @Override
    public ExecutionResult getPartialResult() {
        return partialResult;
    }

    @Override
    public void swapPartialAssignmentWithFullAssignment() {
        result = partialResult.deepCopy();
    }

    @Override
    protected void _run() {
        try {
            doStaticConfigurations();
            configure();
            startExecution();
            finish();
        } finally {
            System.out.println("Execution Ended.");
        }
    }

    private void doStaticConfigurations() {
        mailer.setExecution(this);

        if (getAlgorithm().isUseIdleDetector()) {
            setIdleDetector(new IdleDetector(getGlobalProblem().getNumberOfVariables(), getMailer(), getAlgorithm().getAgentClass().getName()));
        }
    }

    protected void startExecution() {
        while (true) {
            for (int i = 0; i < agentRunners.length; i++) {
                System.out.println("Executing Agent: " + getAgents()[i].getId());
                getExecutorService().execute(getRunners()[i]);
            }
            break;
        }

        for (AgentRunner runner : getRunners()) {
            try {
                runner.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(AsyncExecution.class.getName()).log(Level.SEVERE, null, ex);
                reportCrushAndStop(ex, "interupted while waiting for all agents to finish");
            }
        }
    }

    protected abstract void configure();

    protected abstract void finish();
}