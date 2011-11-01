/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.csp.az.impl.pseq;

import bgu.csp.az.impl.prob.MatrixProblem;
import bgu.csp.az.api.Problem;
import bgu.csp.az.api.pseq.ProblemSequence;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author bennyl
 */
public class RandomProblemSequence implements ProblemSequence {
    public static final String DESCRIPTION_PROBLEM_METADATA = "Description";
    public static final String MAX_COST_PROBLEM_METADATA = "Max Cost";
    public static final String P1_PROBLEM_METADATA = "Probability Of Constraint Between Two Variables";
    public static final String P2_PROBLEM_METADATA = "Probability Of Conflict Between Two Constrained Variables";

    private Random rnd;
    float p1;
    float p2;
    int maxCost;
    int n;
    int d;
    int numberOfProblems;

    public RandomProblemSequence(float p1, float p2, int maxCost, int n, int d, long seed, int numberOfProblems) {
        this.p1 = p1;
        this.p2 = p2;
        this.maxCost = maxCost;
        this.n = n;
        this.d = d;
        this.rnd = new Random(seed);
        this.numberOfProblems = numberOfProblems;
    }

    public RandomProblemSequence(Configuration sd) {
        this.p1 = sd.getP1();
        this.p2 = sd.getP2();
        this.maxCost = sd.getMaxCost();
        this.n = sd.getNumberOfVariables();
        this.d = sd.getDomainSize();
        this.rnd = new Random(sd.getSeed());
        this.numberOfProblems = sd.getProblemsAmount();
    }

    @Override
    public Problem next() {
        numberOfProblems--;

        int[][] mat = new int[n * d][n * d];

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                mat[i][j] = 0;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (rnd.nextDouble() < p1) {
                    buildConstraint(i, j, mat);
                }
            }
        }
        final MatrixProblem problem = new MatrixProblem(mat, n);
        final HashMap<String, Object> metadata = problem.getMetadata();
        metadata.put(P1_PROBLEM_METADATA, p1);
        metadata.put(P2_PROBLEM_METADATA, p2);
        metadata.put(MAX_COST_PROBLEM_METADATA, maxCost);
        metadata.put(DESCRIPTION_PROBLEM_METADATA, "AutoGenerated Random Problem");

        return problem;

    }

    private void buildConstraint(int i, int j, int[][] mat) {
        for (int k = 0; k < d; k++) {
            for (int h = 0; h < d; h++) {
                if (rnd.nextDouble() < p2) {
                    final int cost = rnd.nextInt(maxCost) + 1;
                    mat[i * d + k][j * d + h] = cost;
                    mat[j * d + h][i * d + k] = cost;
                }
            }
        }
    }

    @Override
    public boolean hasNext() {
        return numberOfProblems > 0;
    }

    public static class Configuration implements Serializable {

        private float p1;
        private float p2;
        private int maxCost;
        private int n;
        private int d;
        private int nprob;
        private long seed;

        public Configuration(float p1, float p2, int maxCost, int n, int d, int nprob, long seed) {
            this.p1 = p1;
            this.p2 = p2;
            this.maxCost = maxCost;
            this.n = n;
            this.d = d;
            this.nprob = nprob;
            this.seed = seed;
        }

        public int getDomainSize() {
            return d;
        }

        public int getMaxCost() {
            return maxCost;
        }

        public int getNumberOfVariables() {
            return n;
        }

        public int getProblemsAmount() {
            return nprob;
        }

        public float getP1() {
            return p1;
        }

        public float getP2() {
            return p2;
        }

        public long getSeed() {
            return seed;
        }

        public void setD(int d) {
            this.d = d;
        }

        public void setMaxCost(int maxCost) {
            this.maxCost = maxCost;
        }

        public void setN(int n) {
            this.n = n;
        }

        public void setNprob(int nprob) {
            this.nprob = nprob;
        }

        public void setP1(float p1) {
            this.p1 = p1;
        }

        public void setP2(float p2) {
            this.p2 = p2;
        }

        public void setSeed(long seed) {
            this.seed = seed;
        }
    }
}