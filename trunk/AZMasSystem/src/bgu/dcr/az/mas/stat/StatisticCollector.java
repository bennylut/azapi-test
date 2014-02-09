/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.mas.stat;

import bgu.dcr.az.mas.Execution;
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

    void plot(Plotter ploter);
}