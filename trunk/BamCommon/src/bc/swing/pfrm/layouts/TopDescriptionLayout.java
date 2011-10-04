/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TopDescriptionLayout.java
 *
 * Created on 11/09/2011, 11:21:46
 */
package bc.swing.pfrm.layouts;

import bc.swing.pfrm.Page;
import bc.swing.pfrm.PageView;
import bc.swing.pfrm.BaseParamModel;
import java.awt.BorderLayout;
import java.util.List;

/**
 *
 * @author bennyl
 */
public class TopDescriptionLayout extends javax.swing.JPanel implements PageView{

    public static final String INFO_ROLE = "DESCRIPTION";
    public static final String CONTENT_ROLE = "CONTENT";
    
    /** Creates new form TopDescriptionLayout */
    public TopDescriptionLayout() {
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

        topDescription = new javax.swing.JPanel();
        center = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridBagLayout());

        topDescription.setBackground(new java.awt.Color(245, 245, 245));
        topDescription.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(topDescription, gridBagConstraints);

        center.setOpaque(false);
        center.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        add(center, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel center;
    private javax.swing.JPanel topDescription;
    // End of variables declaration//GEN-END:variables

    public void setPage(Page model) {
        List<BaseParamModel> desc = model.getParamsWithRole(INFO_ROLE);
        List<BaseParamModel> cont = model.getParamsWithRole(CONTENT_ROLE);
        
        for (BaseParamModel i : desc) topDescription.add(i.getDefaultView());
        center.add(cont.get(0).getDefaultView(), BorderLayout.CENTER);
    }

    public void onDispose() {
    }
}
