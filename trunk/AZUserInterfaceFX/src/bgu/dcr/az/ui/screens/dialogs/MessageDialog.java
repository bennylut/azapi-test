/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MessageDialog.java
 *
 * Created on 27/11/2011, 01:34:18
 */
package bgu.dcr.az.ui.screens.dialogs;

import bc.dsl.SwingDSL;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

/**
 *
 * @author kdima85
 */
public class MessageDialog extends javax.swing.JDialog {

     public static enum MessageType {

        INFO,
        SUCCESS,
        FAIL, 
        VALIDATION_FAILD;

        public String getIconName(MessageType type) {
            switch (type) {
                case FAIL:
                    return "fail-message";
                case SUCCESS:
                    return "success-message";
                case VALIDATION_FAILD:
                    return "validation-faild-message";
                case INFO:
                    return "info-message";

            }
            return "info-message";
        }

        public String getTypeText(MessageType type) {
            switch (type) {
                case FAIL:
                    return "ONOES!";
                case SUCCESS:
                    return "WHOOPY!";
                case INFO:
                    return "HMMM...";
                case VALIDATION_FAILD:
                    return "WHOOPS..";

            }


            return "UNKNOWN TYPE OF MESSAGE";
        }
    }

     
    public MessageDialog(java.awt.Frame parent, boolean modal,MessageType type, String data, String content) {
        super(parent, modal);
        initComponents();
        this.messageContentLabel.setText(content);
        this.messageDataLabel.setText(data);
        chooseIconAndType(type);
        setLocationRelativeTo(null);
        initializeKeyBinding();
    }
    public MessageDialog(java.awt.Frame parent, boolean modal,MessageType type, String data, JPanel content) {
        super(parent, modal);
        initComponents();
        this.messageContentPanel.removeAll();
        this.messageContentPanel.add(content,BorderLayout.CENTER);
        this.messageDataLabel.setText(data);
        chooseIconAndType(type);
        setLocationRelativeTo(null);
        initializeKeyBinding();
    }
    
    public void initializeKeyBinding(){
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_ESCAPE:
                        setVisible(false);
                        break;
                }
            }
            
        });
        
        //jPanel1.setFocusTraversalKeysEnabled(true);
        //jPanel1.setFocusable(true);
        
    }
    
    public static void showSuccess(String title, String content){
        MessageDialog x = new MessageDialog(null, true, MessageType.SUCCESS, title, content);
        x.setVisible(true);
    }
    
    public static void showValidationFaild(String description){
        MessageDialog x = new MessageDialog(null, true, MessageType.VALIDATION_FAILD, "input validation failed", description);
        x.setVisible(true);
    }
    
    public static void showFail(String title, String content){
        MessageDialog x = new MessageDialog(null, true, MessageType.FAIL, title, content);
        x.setVisible(true);
    }
    
    private void chooseIconAndType(MessageType messageType) {
        this.typeLable.setText(messageType.getTypeText(messageType));
        this.iconLabel.setIcon(SwingDSL.resIcon(messageType.getIconName(messageType)));
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        iconLabel = new javax.swing.JLabel();
        typeLable = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        messageDescriptionPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        messageDataLabel = new org.jdesktop.swingx.JXLabel();
        messageContentPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        messageContentLabel = new org.jdesktop.swingx.JXLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AgentZero Message (Esc to close)");
        setMinimumSize(new java.awt.Dimension(560, 300));
        setResizable(false);

        jPanel1.setMaximumSize(new java.awt.Dimension(560, 300));
        jPanel1.setMinimumSize(new java.awt.Dimension(560, 300));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(560, 300));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(186, 185, 185)));
        jPanel2.setMinimumSize(new java.awt.Dimension(108, 50));
        jPanel2.setPreferredSize(new java.awt.Dimension(108, 60));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/img/info-message.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(iconLabel, gridBagConstraints);

        typeLable.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        typeLable.setText("HMMM...");
        typeLable.setDoubleBuffered(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel2.add(typeLable, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 5, 5));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        messageDescriptionPanel.setBackground(new java.awt.Color(120, 120, 120));
        messageDescriptionPanel.setOpaque(false);
        messageDescriptionPanel.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(210, 233, 255));
        jLabel4.setText("Description : ");
        messageDescriptionPanel.add(jLabel4, java.awt.BorderLayout.PAGE_START);

        messageDataLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        messageDataLabel.setForeground(new java.awt.Color(255, 255, 255));
        messageDataLabel.setText("Actual Description");
        messageDataLabel.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        messageDataLabel.setLineWrap(true);
        messageDescriptionPanel.add(messageDataLabel, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel3.add(messageDescriptionPanel, gridBagConstraints);

        messageContentPanel.setBackground(new java.awt.Color(120, 120, 120));
        messageContentPanel.setOpaque(false);
        messageContentPanel.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(210, 233, 255));
        jLabel5.setText("Details:");
        messageContentPanel.add(jLabel5, java.awt.BorderLayout.PAGE_START);

        messageContentLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        messageContentLabel.setForeground(new java.awt.Color(255, 255, 255));
        messageContentLabel.setText("Actual Content");
        messageContentLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        messageContentLabel.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        messageContentLabel.setLineWrap(true);
        messageContentPanel.add(messageContentLabel, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel3.add(messageContentPanel, gridBagConstraints);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel iconLabel;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private org.jdesktop.swingx.JXLabel messageContentLabel;
    private javax.swing.JPanel messageContentPanel;
    private org.jdesktop.swingx.JXLabel messageDataLabel;
    private javax.swing.JPanel messageDescriptionPanel;
    private javax.swing.JLabel typeLable;
    // End of variables declaration//GEN-END:variables
}
