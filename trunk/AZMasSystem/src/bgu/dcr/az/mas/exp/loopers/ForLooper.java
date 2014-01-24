/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.mas.exp.loopers;

import bgu.dcr.az.anop.Register;
import bgu.dcr.az.anop.conf.Configuration;
import bgu.dcr.az.anop.conf.Property;
import bgu.dcr.az.anop.conf.impl.FromStringPropertyValue;
import bgu.dcr.az.mas.exp.ExperimentExecutionException;
import bgu.dcr.az.mas.exp.Looper;
import java.util.Collection;

/**
 *
 * @author User
 */
@Register("for-loop")
public class ForLooper implements Looper {

    private Integer count = null;
    private Integer innerCount = 1;
    private Looper innerLooper = new SingleExecutionLooper();
    private String runVar = null;
    private Double startValue = null;
    private Double endValue = null;
    private Double tickSizeValue = null;

    /**
     * @propertyName looper
     * @return the inner looper, if no inner loopers defined returns
     * SingleExecutionLooper
     */
    public Looper getInnerLooper() {
        return innerLooper;
    }

    public void setInnerLooper(Looper looper) throws ExperimentExecutionException {
        this.innerLooper = looper;
        innerCount = looper.count();
        count = null;
    }

    /**
     * @propertyName run-var
     * @return
     */
    public String getRunVar() {
        return runVar;
    }

    public void setRunVar(String propertyName) {
        this.runVar = propertyName;
    }

    /**
     * @propertyName start
     * @return
     */
    public Double getStartValue() {
        return startValue;
    }

    public void setStartValue(Double initialValue) {
        this.startValue = initialValue;
    }

    /**
     * @propertyName end
     * @return
     */
    public Double getEndValue() {
        return endValue;
    }

    public void setEndValue(Double finalValue) {
        this.endValue = finalValue;
    }

    /**
     * @propertyName tick-size
     * @return
     */
    public Double getTickSizeValue() {
        return tickSizeValue;
    }

    public void setTickSizeValue(Double stepValue) {
        this.tickSizeValue = stepValue;
    }

    @Override
    public int count() throws ExperimentExecutionException {
        if (count == null) {
            checkForLoopValues();

            count = 1 + (int) Math.round((endValue - startValue) / tickSizeValue);

            count *= innerCount;
        }

        return count;
    }

    @Override
    public void configure(int i, Collection<Configuration> configurations) throws ExperimentExecutionException {
        checkForLoopValues();
        
        int currI = i / innerCount;

        for (Configuration conf : configurations) {
            Property property = conf.get(runVar);
            if (property != null) {
                Double value = currI >= endValue ? endValue : startValue + tickSizeValue * currI;
                property.set(new FromStringPropertyValue(value.toString()));
            }
        }

        innerLooper.configure(i % innerCount, configurations);
    }

    private void checkForLoopValues() throws ExperimentExecutionException {
        if (startValue == null) {
            throw new ExperimentExecutionException("start property must be declared in order to perform for-loop");
        }

        if (endValue == null) {
            throw new ExperimentExecutionException("end property must be declared in order to perform for-loop");
        }

        if (tickSizeValue == null) {
            throw new ExperimentExecutionException("tick-size property must be declared in order to perform for-loop");
        }

        if (endValue < startValue) {
            throw new ExperimentExecutionException("end must be greater than start in order to perform for-loop");
        }

        if (tickSizeValue <= 0) {
            throw new ExperimentExecutionException("tick-size must be greater 0 in order to perform for-loop");
        }
    }

}
