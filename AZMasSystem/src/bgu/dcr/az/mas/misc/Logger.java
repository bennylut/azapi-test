/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.mas.misc;

import bgu.dcr.az.mas.ExecutionService;
import bgu.dcr.az.mas.cp.CPData;

/**
 *
 * @author User
 */
public interface Logger extends ExecutionService<CPData> {

    void log(String logger, String msg);
}