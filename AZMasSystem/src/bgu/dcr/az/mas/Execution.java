/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.mas;

import bgu.dcr.az.api.exen.ExecutionResult;
import bgu.dcr.az.execs.api.Scheduler;
import bgu.dcr.az.mas.exp.Experiment;
import bgu.dcr.az.mas.exp.ExperimentExecutionException;
import bgu.dcr.az.mas.impl.InitializationException;
import bgu.dcr.az.mas.stat.InfoStream;

/**
 *
 * @author User
 * @param <T>
 */
public interface Execution<T> {

    public <T extends ExecutionService> T require(Class<T> service) throws InitializationException;

    public boolean hasRequirement(Class<? extends ExecutionService> service);
    
    public void supply(Class<? extends ExecutionService> serviceKey, ExecutionService service);

    public ExecutionResult execute(Scheduler sched, int numCores) throws ExperimentExecutionException, InterruptedException;
    
    public ExecutionEnvironment getEnvironment();
    
    public T data();
    
    public Experiment getContainingExperiment();
    
    public InfoStream informationStream();
}