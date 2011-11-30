/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AZView.java
 *
 * Created on 30/09/2011, 09:50:35
 */
package bgu.dcr.az.dev.pui;

import bc.swing.comp.JActionView;
import bc.swing.pfrm.Page;
import bc.swing.pfrm.PageLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 *
 * @author bennyl
 */
public class AZView extends javax.swing.JPanel implements PageLayout{
    public static final String CONSOLE_ROLE = "AZView.CONSOLE_ROLE";
    public static final String PAGES_ROLE = "AZView.PAGES_ROLE";
    public static final String PROGRESS_BAR_ROLE = "AZView.PROGRESS_BAR_ROLE";
    public static final String STOP_AND_SAVE_ACTION = "AZView.STOP_AND_SAVE_ACTION";

    /** Creates new form AZView */
    public AZView() {
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
        java.awt.GridBagConstraints gridBagConstraints;

        pinstripePainter1 = new org.jdesktop.swingx.painter.PinstripePainter();
        backPan = new org.jdesktop.swingx.JXPanel();
        contentPan = new org.jdesktop.swingx.JXPanel();
        topPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        tabs = new javax.swing.JPanel();
        consolePan = new javax.swing.JPanel();
        pbar = new javax.swing.JPanel();

        pinstripePainter1.setCacheable(true);
        pinstripePainter1.setSpacing(3.0);
        pinstripePainter1.setStripeWidth(0.0);

        setLayout(new java.awt.BorderLayout());

        backPan.setBackgroundPainter(pinstripePainter1);
        backPan.setPreferredSize(new java.awt.Dimension(900, 500));
        backPan.setLayout(new java.awt.GridBagLayout());

        contentPan.setBackground(new java.awt.Color(255, 255, 255));
        contentPan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        contentPan.setLayout(new java.awt.GridBagLayout());

        topPanel.setBackground(new java.awt.Color(255, 255, 255));
        topPanel.setPreferredSize(new java.awt.Dimension(100, 50));
        topPanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Nyala", 0, 24));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/logo32.png"))); // NOI18N
        jLabel1.setText("Agent Zero");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        topPanel.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        topPanel.add(jSeparator1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Test Execution Analyzer");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.weightx = 1.0;
        topPanel.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        contentPan.add(topPanel, gridBagConstraints);

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(-1);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.6);

        tabs.setBackground(new java.awt.Color(255, 255, 255));
        tabs.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setLeftComponent(tabs);
        jSplitPane1.setRightComponent(consolePan);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        contentPan.add(jSplitPane1, gridBagConstraints);

        pbar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        pbar.setPreferredSize(new java.awt.Dimension(0, 24));
        pbar.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 3, 5);
        contentPan.add(pbar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.ipady = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        backPan.add(contentPan, gridBagConstraints);

        add(backPan, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXPanel backPan;
    private javax.swing.JPanel consolePan;
    private org.jdesktop.swingx.JXPanel contentPan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel pbar;
    private org.jdesktop.swingx.painter.PinstripePainter pinstripePainter1;
    private javax.swing.JPanel tabs;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setPage(Page model) {
//        PageDSL.fillByRole(model, tabs, PAGES_ROLE);
//        PageDSL.fillByRole(model, pbar, PROGRESS_BAR_ROLE, 3);
        JActionView stopAndSave = new JActionView(model.getAction(STOP_AND_SAVE_ACTION));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.insets = new Insets(3, 3, 3, 3);
        pbar.add(stopAndSave, gbc);
        stopAndSave.setForeground(Color.BLUE);
        
//        PageDSL.fillByRole(model, consolePan, CONSOLE_ROLE);
    }

    @Override
    public void onDispose() {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}