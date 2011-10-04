/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChartPV.java
 *
 * Created on 02/10/2011, 15:09:25
 */
package bc.swing.pfrm.views;

import bc.swing.models.ChartModel;
import bc.swing.pfrm.BaseParamModel;
import bc.swing.pfrm.ParamView;
import java.awt.BorderLayout;
import java.awt.Color;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer2;

/**
 *
 * @author bennyl
 */
public class ChartPV extends javax.swing.JPanel implements ParamView{

    private ChartPanel cpan;
    private ChartModel cmodel;
    private JFreeChart jfchart;
    
    /** Creates new form ChartPV */
    public ChartPV() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void setParam(BaseParamModel param) {
        cmodel = (ChartModel) param.getValue();
        jfchart = cmodel.getChartType().getChart(cmodel);
        cpan = new ChartPanel(jfchart);
        removeAll();
        add(cpan, BorderLayout.CENTER);
        applyStyle();
    }

    public void reflectChangesToParam(BaseParamModel to) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onChange(BaseParamModel source, Object newValue, Object deltaHint) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void applyStyle() {
        jfchart.setBackgroundPaint(Color.white);
    }

    
}
