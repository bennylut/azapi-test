/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainWindow.java
 *
 * Created on 26/11/2011, 12:56:29
 */
package bgu.dcr.az.dev.ui;

import bc.dsl.SwingDSL;
import bgu.dcr.az.api.infra.Execution;
import bgu.dcr.az.api.infra.Experiment;
import bgu.dcr.az.api.infra.Test;
import bgu.dcr.az.dev.ExecutionUnit;
import java.io.File;
import java.util.concurrent.Semaphore;
import javax.swing.JFrame;

/**
 *
 * @author bennyl
 */
public class MainWindow extends javax.swing.JFrame implements Experiment.ExperimentListener {

    private StatusScreen statusScreen;
    private StatisticsScreen statisticsScreen;
    private LogScreen logsScreen;
    private ProblemViewScreen problemScreen;
    private boolean started = false;

    /** Creates new form MainWindow */
    public MainWindow() {
        initComponents();
        executingPanel.setVisible(false);
        errorPanel.setVisible(false);
        donePanel.setVisible(false);
    }

    public void startRunning(Experiment experiment) {
        statusScreen = new StatusScreen();
        statusScreen.setModel(experiment);
        statisticsScreen = new StatisticsScreen();
        statisticsScreen.setModel(experiment);
        problemScreen = new ProblemViewScreen();
        problemScreen.setModel(experiment);
        logsScreen = new LogScreen();
        logsScreen.setModel(experiment);

        //Status Screen!
        tabs.addTab("Status", SwingDSL.resIcon("status"), statusScreen);

        //Statistics Screen!
        tabs.addTab("Statistics", SwingDSL.resIcon("statistics"), statisticsScreen);

        //Logs Screen
        tabs.addTab("Log", SwingDSL.resIcon("balloon-ellipsis"), logsScreen);

        //Problems Screen
        tabs.addTab("Problem", SwingDSL.resIcon("problem"), problemScreen);
        
        ExecutionUnit.UNIT.addExperimentListener(this);

        start();

    }

    private void start() {
        //show!
        if (started) {
            return;
        }

        started = true;
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                setVisible(true);
            }
        });
    }

    public Experiment startDebugging(final Experiment fullExp, File badProblemPath) throws InterruptedException {
        DebugSelectionScreen dsel = new DebugSelectionScreen();
        dsel.setProblemDir(badProblemPath);
        tabs.addTab("Debug", SwingDSL.resIcon("bug"), dsel);
        final Semaphore s = new Semaphore(0);
        final Experiment[] chosen = new Experiment[1];
        dsel.getDebugListeners().addListener(new DebugSelectionScreen.DebugSelectionListener() {

            @Override
            public void onFullExperimentDebugRequested() {
                chosen[0] = fullExp;
                s.release();
            }

            @Override
            public void onSpecificExperimentDebugRequested(Experiment exp) {
                chosen[0] = exp;
//                showProblemView = true; //TODO SHOULD SWITCH TO THE SPECIFIC REQUEST
                s.release();
            }
        });

        start();
        s.acquire();
        tabs.removeTab("Debug");
        return chosen[0];
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
        jPanel1 = new javax.swing.JPanel();
        backPan = new org.jdesktop.swingx.JXPanel();
        contentPan = new org.jdesktop.swingx.JXPanel();
        topPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        donePanel = new javax.swing.JPanel();
        jXHyperlink1 = new org.jdesktop.swingx.JXHyperlink();
        executingPanel = new javax.swing.JPanel();
        jXHyperlink2 = new org.jdesktop.swingx.JXHyperlink();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        errorPanel = new javax.swing.JPanel();
        jXHyperlink3 = new org.jdesktop.swingx.JXHyperlink();
        tabs = new bc.ui.swing.tabs.Tabs();

        pinstripePainter1.setCacheable(true);
        pinstripePainter1.setSpacing(3.0);
        pinstripePainter1.setStripeWidth(0.0);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Agent Zero Testing Session");
        setBackground(new java.awt.Color(51, 51, 51));

        jPanel1.setLayout(new java.awt.BorderLayout());

        backPan.setBackground(new java.awt.Color(51, 51, 51));
        backPan.setBackgroundPainter(pinstripePainter1);
        backPan.setPreferredSize(new java.awt.Dimension(900, 500));
        backPan.setLayout(new java.awt.GridBagLayout());

        contentPan.setBackground(new java.awt.Color(255, 255, 255));
        contentPan.setOpaque(false);
        contentPan.setLayout(new java.awt.GridBagLayout());

        topPanel.setBackground(new java.awt.Color(51, 51, 51));
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new java.awt.Dimension(100, 40));
        topPanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/logo35.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        topPanel.add(jLabel1, gridBagConstraints);

        donePanel.setOpaque(false);
        donePanel.setLayout(new java.awt.GridBagLayout());

        jXHyperlink1.setForeground(new java.awt.Color(51, 51, 51));
        jXHyperlink1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/done.png"))); // NOI18N
        jXHyperlink1.setText("");
        jXHyperlink1.setClickedColor(new java.awt.Color(51, 51, 51));
        jXHyperlink1.setUnclickedColor(new java.awt.Color(51, 51, 51));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        donePanel.add(jXHyperlink1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        topPanel.add(donePanel, gridBagConstraints);

        executingPanel.setOpaque(false);
        executingPanel.setLayout(new java.awt.GridBagLayout());

        jXHyperlink2.setForeground(new java.awt.Color(51, 51, 51));
        jXHyperlink2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/cross-button.png"))); // NOI18N
        jXHyperlink2.setText("");
        jXHyperlink2.setClickedColor(new java.awt.Color(51, 51, 51));
        jXHyperlink2.setUnclickedColor(new java.awt.Color(51, 51, 51));
        jXHyperlink2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXHyperlink2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        executingPanel.add(jXHyperlink2, gridBagConstraints);

        jLabel4.setBackground(new java.awt.Color(245, 245, 245));
        jLabel4.setForeground(new java.awt.Color(240, 240, 240));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Executing");
        jLabel4.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        executingPanel.add(jLabel4, gridBagConstraints);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/progress.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        executingPanel.add(jLabel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        topPanel.add(executingPanel, gridBagConstraints);

        errorPanel.setOpaque(false);
        errorPanel.setLayout(new java.awt.GridBagLayout());

        jXHyperlink3.setForeground(new java.awt.Color(51, 51, 51));
        jXHyperlink3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/error.png"))); // NOI18N
        jXHyperlink3.setText("");
        jXHyperlink3.setClickedColor(new java.awt.Color(51, 51, 51));
        jXHyperlink3.setUnclickedColor(new java.awt.Color(51, 51, 51));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        errorPanel.add(jXHyperlink3, gridBagConstraints);

        topPanel.add(errorPanel, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        contentPan.add(topPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        contentPan.add(tabs, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.ipady = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        backPan.add(contentPan, gridBagConstraints);

        jPanel1.add(backPan, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jXHyperlink2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXHyperlink2ActionPerformed
        ExecutionUnit.UNIT.stop();
    }//GEN-LAST:event_jXHyperlink2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        SwingDSL.configureUI();
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXPanel backPan;
    private org.jdesktop.swingx.JXPanel contentPan;
    private javax.swing.JPanel donePanel;
    private javax.swing.JPanel errorPanel;
    private javax.swing.JPanel executingPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private org.jdesktop.swingx.JXHyperlink jXHyperlink1;
    private org.jdesktop.swingx.JXHyperlink jXHyperlink2;
    private org.jdesktop.swingx.JXHyperlink jXHyperlink3;
    private org.jdesktop.swingx.painter.PinstripePainter pinstripePainter1;
    private bc.ui.swing.tabs.Tabs tabs;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onExpirementStarted(Experiment source) {
        executingPanel.setVisible(true);
        errorPanel.setVisible(false);
        donePanel.setVisible(false);
    }

    @Override
    public void onExpirementEnded(Experiment source) {
        executingPanel.setVisible(false);

        if (source.getResult().succeded) {
            donePanel.setVisible(true);
            MessageDialog.showSuccess("The execution completed successfully", "There were no errors\n"
                    + "If you've defined correctness testers they didn't find any wrong solutions.");
        } else {
            errorPanel.setVisible(true);
            switch (source.getResult().badTestResult.finishStatus) {
                case CRUSH:
                    ExceptionDialog.showRecoverable("The execution crushed", "You should take a look at the logs,\n"
                            + "check the stack trace using the advance button\n"
                            + "or start a debug session", source.getResult().badTestResult.crushReason);
                    break;
                case WRONG_RESULT:
                    MessageDialog.showFail("The execution completed with errors", "The correctness tester found wrong results provided by the algorithm");
                    break;
            }
        }
    }

    @Override
    public void onNewTestStarted(Experiment source, Test test) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onNewExecutionStarted(Experiment source, Test test, Execution exec) {
//        if (showProblemView) {
//            problemScreen = new ProblemViewScreen(exec.getGlobalProblem());
//            tabs.addTab("Problem", SwingDSL.resIcon("problem"), problemScreen);
//        }
    }

    @Override
    public void onExecutionEnded(Experiment source, Test test, Execution exec) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
