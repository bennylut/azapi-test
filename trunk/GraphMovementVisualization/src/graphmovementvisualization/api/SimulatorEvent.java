/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphmovementvisualization.api;

import java.util.Collection;

/**
 *
 * @author Shl
 */
public interface SimulatorEvent {
    
    public String getName();
    
    public Collection<? extends Object> getParameters();
    
}