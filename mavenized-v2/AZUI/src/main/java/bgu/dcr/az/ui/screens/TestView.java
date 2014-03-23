/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TestView.java
 *
 * Created on 27/11/2011, 02:49:49
 */
package bgu.dcr.az.ui.screens;

import bc.ui.swing.visuals.Visual;
import bc.ui.swing.visuals.Visual.VisualGen;
import bgu.dcr.az.anop.conf.Configuration;
import bgu.dcr.az.anop.conf.ConfigurationException;
import bgu.dcr.az.anop.conf.ConfigurationUtils;
import bgu.dcr.az.anop.conf.Property;
import bgu.dcr.az.anop.conf.impl.FromConfigurationPropertyValue;
import bgu.dcr.az.mas.cp.CPExperimentTest;
import bgu.dcr.az.mas.exp.AlgorithmDef;
import bgu.dcr.az.mas.exp.Experiment;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bennyl
 */
public class TestView extends javax.swing.JPanel {

    private VisualGen varsGen;
    private List<DebugRequestListener> debugListeners = new LinkedList<DebugRequestListener>();

    /**
     * Creates new form TestView
     */
    public TestView() {
        initComponents();
        failurePan.setVisible(false);
        debugProblemButtonPan.setVisible(false);
        testVars.getList().setFixedCellHeight(16);
        pgenVars.getList().setFixedCellHeight(16);
        algos.getList().setFixedCellHeight(16);

        varsGen = new Visual.VisualGen() {

            @Override
            public Visual gen(Object it) {
                Property var = (Property) it;
                return new Visual(it, "<html>+ <b>" + var.name() + "</b>=<b><span color='#d2e9ff'>'" + var.stringValue() + "'</span></b> [ " + var.doc().description() + " ]</html>", "", null);
            }
        };
    }

    public void addDebugRequestListener(DebugRequestListener l) {
        debugListeners.add(l);
    }

    public void setModel(Experiment e) {
        try {
            Configuration eConf = ConfigurationUtils.load(e);
            Configuration pConf = ((FromConfigurationPropertyValue) eConf.get("problem-generator").get()).getValue();

            testVars.setItems(Visual.adapt(ConfigurationUtils.getAttributesOf(eConf), varsGen));
            pgenVars.setItems(Visual.adapt(ConfigurationUtils.getAttributesOf(pConf), varsGen));
            algos.setItems(Visual.adapt(((CPExperimentTest) e).getAlgorithms(), new VisualGen() {

                @Override
                public Visual gen(Object it) {
                    AlgorithmDef alg = (AlgorithmDef) it;
                    return new Visual(alg, "<html>+ " + alg.getName() + " <b><span color='#d2e9ff'>as </span></b>" + alg.getInstanceName() + "</html>", "", null);
                }
            }));
        } catch (ClassNotFoundException | ConfigurationException ex) {
            Logger.getLogger(TestView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        spacer = new org.jdesktop.swingx.JXLabel();
        jXLabel2 = new org.jdesktop.swingx.JXLabel();
        jPanel2 = new javax.swing.JPanel();
        testVars = new bc.ui.swing.lists.TransparentList();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        spacer1 = new org.jdesktop.swingx.JXLabel();
        jXLabel3 = new org.jdesktop.swingx.JXLabel();
        jPanel6 = new javax.swing.JPanel();
        pgenVars = new bc.ui.swing.lists.TransparentList();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        spacer2 = new org.jdesktop.swingx.JXLabel();
        jXLabel4 = new org.jdesktop.swingx.JXLabel();
        jPanel9 = new javax.swing.JPanel();
        algos = new bc.ui.swing.lists.TransparentList();
        failurePan = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        spacer3 = new org.jdesktop.swingx.JXLabel();
        jXLabel5 = new org.jdesktop.swingx.JXLabel();
        jPanel12 = new javax.swing.JPanel();
        failureData = new bc.ui.swing.lists.TransparentList();
        spacerPan = new javax.swing.JPanel();
        debugProblemButtonPan = new javax.swing.JPanel();
        jXHyperlink1 = new org.jdesktop.swingx.JXHyperlink();

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/gnome-glchess_1.png"))); // NOI18N
        jButton1.setText("Debug This Problem");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        setBackground(new java.awt.Color(102, 102, 102));
        setLayout(new java.awt.GridBagLayout());

        jPanel3.setMinimumSize(new java.awt.Dimension(155, 150));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(549, 115));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel1.setMinimumSize(new java.awt.Dimension(200, 16));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 100));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Test Parameters");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jLabel1, gridBagConstraints);

        spacer.setMinimumSize(new java.awt.Dimension(20, 0));
        spacer.setPreferredSize(new java.awt.Dimension(20, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        jPanel1.add(spacer, gridBagConstraints);

        jXLabel2.setForeground(new java.awt.Color(210, 233, 255));
        jXLabel2.setText("the set of parameters controlling the execution of the test");
        jXLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jXLabel2.setLineWrap(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jXLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel3.add(jPanel1, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(349, 100));
        jPanel2.setLayout(new java.awt.BorderLayout());

        testVars.setForeColor(new java.awt.Color(255, 255, 255));
        jPanel2.add(testVars, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jPanel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(jPanel3, gridBagConstraints);

        jPanel4.setMinimumSize(new java.awt.Dimension(155, 150));
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(549, 115));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel5.setMinimumSize(new java.awt.Dimension(200, 16));
        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(200, 100));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Problem Generator");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel5.add(jLabel2, gridBagConstraints);

        spacer1.setMinimumSize(new java.awt.Dimension(20, 0));
        spacer1.setPreferredSize(new java.awt.Dimension(20, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        jPanel5.add(spacer1, gridBagConstraints);

        jXLabel3.setForeground(new java.awt.Color(210, 233, 255));
        jXLabel3.setText("the generator of the  problems in this test");
        jXLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jXLabel3.setLineWrap(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel5.add(jXLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jPanel5, gridBagConstraints);

        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel6.setOpaque(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(349, 100));
        jPanel6.setLayout(new java.awt.BorderLayout());

        pgenVars.setForeColor(new java.awt.Color(255, 255, 255));
        jPanel6.add(pgenVars, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(jPanel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        add(jPanel4, gridBagConstraints);

        jPanel7.setMinimumSize(new java.awt.Dimension(155, 150));
        jPanel7.setOpaque(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(549, 115));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel8.setMinimumSize(new java.awt.Dimension(200, 16));
        jPanel8.setOpaque(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(200, 100));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Executed Algorithms");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel8.add(jLabel3, gridBagConstraints);

        spacer2.setMinimumSize(new java.awt.Dimension(20, 0));
        spacer2.setPreferredSize(new java.awt.Dimension(20, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        jPanel8.add(spacer2, gridBagConstraints);

        jXLabel4.setForeground(new java.awt.Color(210, 233, 255));
        jXLabel4.setText("list of the algorithms that  participating in this test");
        jXLabel4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jXLabel4.setLineWrap(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel8.add(jXLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel7.add(jPanel8, gridBagConstraints);

        jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel9.setOpaque(false);
        jPanel9.setPreferredSize(new java.awt.Dimension(349, 100));
        jPanel9.setLayout(new java.awt.BorderLayout());

        algos.setForeColor(new java.awt.Color(255, 255, 255));
        jPanel9.add(algos, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel7.add(jPanel9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        add(jPanel7, gridBagConstraints);

        failurePan.setMinimumSize(new java.awt.Dimension(155, 150));
        failurePan.setOpaque(false);
        failurePan.setPreferredSize(new java.awt.Dimension(549, 115));
        failurePan.setLayout(new java.awt.GridBagLayout());

        jPanel11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel11.setMinimumSize(new java.awt.Dimension(200, 16));
        jPanel11.setOpaque(false);
        jPanel11.setPreferredSize(new java.awt.Dimension(200, 100));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel4.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Failure Description");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel11.add(jLabel4, gridBagConstraints);

        spacer3.setMinimumSize(new java.awt.Dimension(20, 0));
        spacer3.setPreferredSize(new java.awt.Dimension(20, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        jPanel11.add(spacer3, gridBagConstraints);

        jXLabel5.setForeground(new java.awt.Color(210, 233, 255));
        jXLabel5.setText("the data about the failure\nthis is whats controlling the debugging ");
        jXLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jXLabel5.setLineWrap(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel11.add(jXLabel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        failurePan.add(jPanel11, gridBagConstraints);

        jPanel12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel12.setOpaque(false);
        jPanel12.setPreferredSize(new java.awt.Dimension(349, 100));
        jPanel12.setLayout(new java.awt.BorderLayout());

        failureData.setForeColor(new java.awt.Color(255, 255, 255));
        jPanel12.add(failureData, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        failurePan.add(jPanel12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        add(failurePan, gridBagConstraints);

        spacerPan.setMinimumSize(new java.awt.Dimension(155, 150));
        spacerPan.setOpaque(false);
        spacerPan.setLayout(new java.awt.GridBagLayout());

        debugProblemButtonPan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        debugProblemButtonPan.setOpaque(false);
        debugProblemButtonPan.setLayout(new java.awt.GridBagLayout());

        jXHyperlink1.setForeground(new java.awt.Color(255, 255, 255));
        jXHyperlink1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/gnome-glchess_1.png"))); // NOI18N
        jXHyperlink1.setText("Debug This Problem");
        jXHyperlink1.setClickedColor(new java.awt.Color(255, 255, 255));
        jXHyperlink1.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        jXHyperlink1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jXHyperlink1.setUnclickedColor(new java.awt.Color(255, 255, 255));
        jXHyperlink1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jXHyperlink1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXHyperlink1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        debugProblemButtonPan.add(jXHyperlink1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        spacerPan.add(debugProblemButtonPan, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        add(spacerPan, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jXHyperlink1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXHyperlink1ActionPerformed
        for (DebugRequestListener l : debugListeners) {
            l.onDebugRequested();
        }
    }//GEN-LAST:event_jXHyperlink1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private bc.ui.swing.lists.TransparentList algos;
    private javax.swing.JPanel debugProblemButtonPan;
    private bc.ui.swing.lists.TransparentList failureData;
    private javax.swing.JPanel failurePan;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private org.jdesktop.swingx.JXHyperlink jXHyperlink1;
    private org.jdesktop.swingx.JXLabel jXLabel2;
    private org.jdesktop.swingx.JXLabel jXLabel3;
    private org.jdesktop.swingx.JXLabel jXLabel4;
    private org.jdesktop.swingx.JXLabel jXLabel5;
    private bc.ui.swing.lists.TransparentList pgenVars;
    private org.jdesktop.swingx.JXLabel spacer;
    private org.jdesktop.swingx.JXLabel spacer1;
    private org.jdesktop.swingx.JXLabel spacer2;
    private org.jdesktop.swingx.JXLabel spacer3;
    private javax.swing.JPanel spacerPan;
    private bc.ui.swing.lists.TransparentList testVars;
    // End of variables declaration//GEN-END:variables

//    public void addFailureData(ExecutionSelector di) {
//        this.failurePan.setVisible(true);
//        this.debugProblemButtonPan.setVisible(true);
//        
//        failureData.setItems(Visual.adapt(VariableMetadata.scan(di), varsGen));
//    }
    public static interface DebugRequestListener {

        void onDebugRequested();
    }
}
