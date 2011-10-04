/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PageTabsPV.java
 *
 * Created on 15/09/2011, 02:00:34
 */
package bc.swing.pfrm.views;

import bc.swing.pfrm.Model;
import bc.swing.pfrm.Page;
import bc.swing.pfrm.ListChangeDeltaHint;
import bc.swing.pfrm.BaseParamModel;
import bc.swing.pfrm.ParamView;
import java.util.List;

/**
 *
 * @author bennyl
 */
public class TabsPV extends javax.swing.JPanel implements ParamView {

    /** Creates new form PageTabsPV */
    public TabsPV() {
        initComponents();
    }

    public void fillTabs(List models) {
        for (Object omodel : models) {
            Model model = (Model) omodel;
            Page p = Page.get(model);
            tabs.addTab(p.getName(), p.getIcon(), p.getView());
        }
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

        tabs = new javax.swing.JTabbedPane();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridBagLayout());

        tabs.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 1, 0);
        add(tabs, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabs;
    // End of variables declaration//GEN-END:variables

    public void setParam(BaseParamModel pmod) {
        List models = (List) pmod.getValue();
        fillTabs(models);
    }

    public void reflectChangesToParam(BaseParamModel to) {
        List<Model> models = (List<Model>) to.getValue();
        for (Model model : models) {
            Page.get(model).syncParametersFromView();
        }
    }

    public void onChange(BaseParamModel source, Object newValue, Object deltaHint) {
        if (deltaHint != null) {
            ListChangeDeltaHint dhint = (ListChangeDeltaHint) deltaHint;

            switch (dhint.type) {
                case ListChangeDeltaHint.LAST_ITEM_ADDED_TYPE:
                    List l = (List)newValue;
                    Page p = Page.get((Model)l.get(l.size()-1));
                    tabs.addTab(p.getName(), p.getIcon(), p.getView());
                    break;
            }
        }else {
            tabs.removeAll();
            fillTabs((List)newValue);
        }
    }
}
