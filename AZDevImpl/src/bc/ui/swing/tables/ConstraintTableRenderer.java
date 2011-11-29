/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConstraintTableRenderer.java
 *
 * Created on 30/11/2011, 00:45:57
 */
package bc.ui.swing.tables;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author bennyl
 */
public class ConstraintTableRenderer extends javax.swing.JPanel implements TableCellRenderer {

    private static final Color CONSTRAINT_COLOR = new Color(215, 62, 51);
    private static final Color HEADER_COLOR = new Color(139, 164, 193);
    private static final Color UNCONSTRAINT_COLOR = new Color(102, 102, 102);

    /** Creates new form ConstraintTableRenderer */
    public ConstraintTableRenderer() {
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

        text = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 153, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 3, 3));

        text.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        text.setForeground(new java.awt.Color(255, 255, 255));
        text.setText("jLabel1");
        add(text);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel text;
    // End of variables declaration//GEN-END:variables

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        if (value != null) {
            text.setText(value.toString());
            if (value.toString().equals("0")) {
                setBackground(UNCONSTRAINT_COLOR);
            } else {
                setBackground(CONSTRAINT_COLOR);
            }
        }else {
            text.setText("");
        }

        if (row == 0 || column == 0) {
            setBackground(HEADER_COLOR);
        }
        return this;
    }
}
