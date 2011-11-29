/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.csp.az.impl.pgen;

import bgu.csp.az.api.Agt0DSL;
import bgu.csp.az.api.ProblemType;
import bgu.csp.az.api.ano.Register;
import bgu.csp.az.api.ds.ImmutableSet;
import bgu.csp.az.api.pgen.Problem;
import java.util.Random;

/**
 *
 * @author bennyl
 */
@Register(name="adcop-unstructured")
public class UnstructuredADCOPGen extends UnstructuredDCOPGen {

    @Override
    public void generate(Problem p, Random rand) {
        p.initialize(ProblemType.ADCOP, n, new ImmutableSet<Integer>(Agt0DSL.range(0, d - 1)));
        for (int i = 0; i < p.getNumberOfVariables(); i++) {
            for (int j = 0; j < p.getNumberOfVariables(); j++) {
                if (rand.nextDouble() < p1) {
                    buildConstraint(i, j, p, false, rand, p2);
                }
            }
        }
    }

}