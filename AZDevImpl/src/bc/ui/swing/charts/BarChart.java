/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BarChart.java
 *
 * Created on 10/12/2011, 01:40:54
 */
package bc.ui.swing.charts;

import bgu.dcr.az.api.exen.stat.vmod.BarVisualModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;
import java.util.Map.Entry;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author bennyl
 */
public class BarChart extends javax.swing.JPanel {

    BarVisualModel model;
    DefaultCategoryDataset dataset;
    private ChartPanel chartPanel;

    /** Creates new form BarChart */
    public BarChart() {
        initComponents();
    }

    public void setModel(BarVisualModel model) {
        this.model = model;
        dataset = new DefaultCategoryDataset();
        for (String a : model.getAlgorithms()) {
            for (Entry<String, Double> v : model.getValues(a).entrySet()) {
                dataset.addValue(v.getValue(), a, v.getKey());
            }
        }

        removeAll();
        chartPanel = new ChartPanel(generateView());
        add(chartPanel, BorderLayout.CENTER);

    }

    private JFreeChart generateView() {
        JFreeChart ret = ChartFactory.createBarChart(
                model.getTitle(),
                model.getDomainAxisLabel(),
                model.getRangeAxisLabel(),
                ((DefaultCategoryDataset) dataset),
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        ret.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) ret.getPlot();
        plot.setBackgroundPaint(Color.white);

        plot.setDomainGridlinePaint(new Color(200, 200, 200));
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(new Color(200, 200, 200));
        plot.setRangeGridlinesVisible(true);
        BarRenderer barrenderer = (BarRenderer) plot.getRenderer();

        barrenderer.setDrawBarOutline(true);
        barrenderer.setBarPainter(new StandardBarPainter());


        final Color baseColor = new Color(160, 200, 255);
        final DefaultDrawingSupplier otherColors = new DefaultDrawingSupplier();
        plot.setDrawingSupplier(new DefaultDrawingSupplier() {

            boolean first = true;

            @Override
            public Paint getNextPaint() {
                if (first) {
                    first = false;
                    return baseColor;
                }
                return otherColors.getNextPaint();
            }
        });

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        domainAxis.setTickMarksVisible(true);

        return ret;
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
}
