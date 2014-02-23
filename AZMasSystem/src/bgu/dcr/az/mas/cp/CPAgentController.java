/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.mas.cp;

import bgu.dcr.az.anop.algo.AgentManipulator;
import bgu.dcr.az.anop.conf.ConfigurationException;
import bgu.dcr.az.api.Agent;
import bgu.dcr.az.api.Agt0DSL;
import bgu.dcr.az.api.prob.Problem;
import bgu.dcr.az.api.tools.Assignment;
import bgu.dcr.az.mas.Execution;
import bgu.dcr.az.mas.impl.BaseAgentController;
import bgu.dcr.az.mas.impl.InitializationException;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class CPAgentController extends BaseAgentController {

    private final CPExecution execution;

    public CPAgentController(int id, CPExecution ex) throws ClassNotFoundException, ConfigurationException, InitializationException {
        super(id, ex);

        this.execution = ex;
    }

    public Problem getGlobalProblem() {
        return execution.data().getProblem();
    }

    public void report(String who, Agent a, Object[] data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void assign(int id, int value) {
        execution.data().getSolution().assign(id, value);
    }

    public void unassign(int id) {
        execution.data().getSolution().unassign(id);
    }

    public Integer getAssignment(int id) {
        Integer result = execution.data().getSolution().assignmentOf(id);
        if (result == null) {
            Agt0DSL.panic("attempting to get assignment when no such exists ( agent: " + id + ")");
        }

        return result;
    }

    public void assignAll(Assignment a) {
        execution.data().getSolution().assignAll(a);
    }

    @Override
    protected void initializeAgent(Agent agent, AgentManipulator manipulator, int aId, Execution ex) {
        Agent.PlatformOperationsExtractor.extract(agent).initialize(aId, this, ex);
    }

    public void reportNoSolution() {
        execution.data().getSolution().setStateNoSolution();
    }

    @Override
    protected void handleIdle() {
        LinkedList<AgentWithManipulator> killedAgents = new LinkedList<>();
        for (AgentWithManipulator a : getControlledAgents().values()) {
            a.a.onIdleDetected();
            if (a.a.isFinished()) {
                killedAgents.add(a);
            }
        }

        for (AgentWithManipulator k : killedAgents) {
            removeControlledAgent(k);
        }

    }

    @Override
    protected void beforeNextTick() {
        LinkedList<AgentWithManipulator> killedAgents = new LinkedList<>();
        for (AgentWithManipulator a : getControlledAgents().values()) {
            a.a.onMailBoxEmpty();
            if (a.a.isFinished()) {
                killedAgents.add(a);
            }
        }

        for (AgentWithManipulator k : killedAgents) {
            removeControlledAgent(k);
        }

    }

}