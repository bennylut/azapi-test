/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.csp.az.api;

/**
 *
 * @author Inna
 */
public class ReportMediator {
    Object[] args;
    Agent a;

    public ReportMediator(Object[] args, Agent a) {
        this.args = args;
        this.a = a;
    }
    
    public void to(String who){
        Agent.PlatformOperationsExtractor.extract(a).getExecution().report(who, a, args);
    }
}
