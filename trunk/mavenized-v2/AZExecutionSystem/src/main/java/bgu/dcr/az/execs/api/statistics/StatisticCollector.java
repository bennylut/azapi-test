/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.execs.api.statistics;

import bgu.dcr.az.execs.api.experiments.Execution;
import bgu.dcr.az.execs.api.experiments.Experiment;
import bgu.dcr.az.orm.api.DefinitionDatabase;
import bgu.dcr.az.orm.api.QueryDatabase;

/**
 *
 * @author User
 * @param <T>
 */
public interface StatisticCollector<T> {

    void initialize(StatisticsManager manager, Execution<T> execution, DefinitionDatabase database);

    String getName();

    void plot(Plotter ploter, Experiment experiment);
}