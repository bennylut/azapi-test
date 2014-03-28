/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.mas.cp;

import bgu.dcr.az.mas.ExecutionEnvironment;
import bgu.dcr.az.anop.conf.ConfigurationException;
import bgu.dcr.az.api.prob.Problem;
import bgu.dcr.az.execs.api.Proc;
import bgu.dcr.az.mas.AgentDistributer;
import bgu.dcr.az.mas.AgentSpawner;
import bgu.dcr.az.mas.exp.AlgorithmDef;
import bgu.dcr.az.mas.exp.Experiment;
import bgu.dcr.az.mas.impl.BaseExecution;
import bgu.dcr.az.mas.impl.BaseMessageRouter;
import bgu.dcr.az.mas.impl.InitializationException;
import bgu.dcr.az.mas.impl.misc.StdoutLogger;
import bgu.dcr.az.mas.misc.Logger;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author User
 */
public class CPExecution extends BaseExecution<CPData> {

    public CPExecution(Experiment containingExperiment, AlgorithmDef a, double runningVariable, AgentSpawner spawner, Problem problem, ExecutionEnvironment env) {
        super(new CPData(new CPSolution(problem), problem, a, runningVariable), containingExperiment, env, problem.getAgentDistributer(), spawner, new BaseMessageRouter());
    }

    @Override
    protected Collection<Proc> createProcesses() throws InitializationException {
        List<Proc> controllers = new LinkedList<>();

        AgentDistributer distributer = require(AgentDistributer.class);
        for (int i = 0; i < distributer.getNumberOfAgentControllers(); i++) {
            try {
                final CPAgentController controller = new CPAgentController(i, this);
                controller.setGiveupBeforeComplete(getEnvironment() == ExecutionEnvironment.async);
                controllers.add(controller);
            } catch (ClassNotFoundException | ConfigurationException ex) {
                throw new InitializationException("could not initialize agent, see cause", ex);
            }
        }

        return controllers;
    }

    @Override
    protected void initialize() {
        if (!hasRequirement(Logger.class)) {
            supply(Logger.class, new StdoutLogger());
        }
    }

}