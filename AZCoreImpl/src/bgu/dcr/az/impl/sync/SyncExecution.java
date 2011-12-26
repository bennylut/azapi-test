/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.impl.sync;

import bgu.dcr.az.api.infra.Experiment;
import bgu.dcr.az.api.infra.Test;
import bgu.dcr.az.impl.AlgorithmMetadata;
import bgu.dcr.az.api.pgen.Problem;
import bgu.dcr.az.impl.infra.AbstractExecution;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author bennyl
 */
public class SyncExecution extends AbstractExecution {

    public SyncExecution(Problem p, AlgorithmMetadata a, Test r, Experiment exp) {
        super(p, new SyncMailer(), a, r, exp);
    }

    @Override
    protected void configure() {
        DefaultSystemClock clock = new DefaultSystemClock();
        setSystemClock(clock);
        ((SyncMailer) getMailer()).setClock(clock);
        final int numberOfVariables = getGlobalProblem().getNumberOfVariables();
        final int numberOfCores = Runtime.getRuntime().availableProcessors();
        final int numberOfAgentRunners = Math.min(numberOfCores, numberOfVariables);

        /**
         * THIS EXECUTION MOD USES AGENT RUNNER IN POOL MODE
         */
        if (!generateAgents()) {
            return;
        }

        setAgentRunners(SyncAgentRunner.createAgentRunners(numberOfAgentRunners, getSystemClock(), this, getAgents()));
        clock.setExcution(this); //MUST BE CALLED AFTER THE AGENT RUNNERS HAVE BEEN ASSIGNED...
    }

    @Override
    protected void finish() {
    }
}
