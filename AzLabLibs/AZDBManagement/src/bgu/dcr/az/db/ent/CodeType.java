/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.db.ent;

import javax.persistence.Embeddable;

/**
 *
 * @author Inka
 */
@Embeddable
public enum CodeType {

    TOOL, AGENT, PROBLEM_GENERATOR, STATISTIC_COLLECTOR, CORRECTNESS_TESTER, MESSAGE_DELAYER, LIMITER;
}