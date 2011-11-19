/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Console.java
 *
 * Created on Aug 23, 2011, 10:09:50 PM
 */
package bc.swing.comp;

import bc.swing.models.ConsoleModel;

/**
 *
 * @author BLutati
 */
public class Console extends javax.swing.JPanel implements ConsoleModel.Listener{

    /** Creates new form Console */
    public Console() {
        initComponents();
    }

    public void setModel(ConsoleModel model){
        model.addListener(this);
        // Make sure the last line is always visible
        console.setDocument(model.getDocument());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);

        console.setBackground(new java.awt.Color(51, 51, 51));
        console.setColumns(20);
        console.setEditable(false);
        console.setFont(new java.awt.Font("Courier New", 0, 14));
        console.setForeground(new java.awt.Color(51, 255, 0));
        console.setRows(5);
        jScrollPane1.setViewportView(console);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea console;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onTextAppended(ConsoleModel source, String append) {
        console.setCaretPosition(console.getDocument().getLength());
    }

}