/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StringWithTitle.java
 *
 * Created on 13/09/2011, 04:48:26
 */
package bc.swing.pfrm.params.views;

import bc.swing.pfrm.params.ParamModel;
import bc.swing.pfrm.params.ParamView;

/**
 *
 * @author bennyl
 */
public class StringWithTitlePV extends javax.swing.JPanel implements ParamView {

    /** Creates new form StringWithTitle */
    public StringWithTitlePV() {
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

        title = new javax.swing.JLabel();
        field = new javax.swing.JTextField();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        title.setText("jLabel1");
        add(title, new java.awt.GridBagConstraints());

        field.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        add(field, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField field;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables

    public void setModel(ParamModel model) {
        title.setText(model.getName());
        title.setIcon(model.getIcon());
        field.setText(model.getValue().toString());
    }

    public void reflectChanges(ParamModel to) {
        to.setValue(field.getText());
    }

    public void onChange(ParamModel source, Object newValue, Object deltaHint) {
        field.setText(newValue.toString());
    }
}