/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.csp.az.api.ano;

import bgu.csp.az.api.ProblemType;
import bgu.csp.az.api.SearchType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation to define the algorithm metadata
 * @author bennyl
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Algorithm {

    /**
     * @return the algorithm name
     */
    String name();
    
    /**
     * @return the type of problem this algorithm solve
     */
    ProblemType problemType() default ProblemType.DCOP;

    /**
     * if this set to true the algorithm requires an idle detector
     * @See bgu.csp.az.api.tools.IdleDetector
     */
    boolean useIdleDetector() default false;
    
    SearchType searchType() default SearchType.ASYNCHRONOUS; 

}
