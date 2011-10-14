/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PageWizard.java
 *
 * Created on 13/09/2011, 02:45:36
 */
package bc.swing.pfrm.layouts;

import bc.swing.pfrm.Page;
import bc.swing.pfrm.PageLayout;
import bc.swing.pfrm.BaseParamModel;
import bc.swing.pfrm.FieldParamModel;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author bennyl
 */
public class PageWizard extends javax.swing.JPanel implements PageLayout {

    public static final String WIZARD_PAGE_ROLE = "WIZARD_PAGE";
    
    Page page;
    ArrayList<String> names;
    int currentPageIndex;

    /** Creates new form PageWizard */
    public PageWizard() {
        initComponents();
    }
    
    
    public void setCaption(String caption, ImageIcon icon){
        captionLabel.setText(caption);
        captionLabel.setIcon(icon);
    }


    public void onActivePageChanged() {
        String active = names.get(currentPageIndex);
        ((CardLayout)pages.getLayout()).show(pages, active);
        pageCaptionLabel.setText(active);
        BaseParamModel pmod = page.getParam(active);
        pageCaptionLabel.setIcon(pmod.getIcon());
        
        prevButton.setVisible(true);
        nextButton.setVisible(true);
        finishButton.setVisible(false);

        final int pidx = page.getParamIndex(active);
        if (pidx == 0){
            prevButton.setVisible(false);
        }

        if (pidx == page.getParams().size()-1){
            nextButton.setVisible(false);
            finishButton.setVisible(true);
        }
    }

    public void setPage(Page page) {
        this.page = page;
        
        
        List<BaseParamModel> mpages = page.getParamsWithRole(WIZARD_PAGE_ROLE);
        names = new ArrayList<String>(mpages.size());
        
        for (BaseParamModel mp : mpages){
            //Model m = (Model) mp.getValue();
            names.add(mp.getName());
            JPanel p = (JPanel) mp.getDefaultView();
            pages.add(mp.getName(), p);
        }
        
        
        currentPageIndex = 0;
        onActivePageChanged();
        this.setCaption(page.getName(), page.getIcon());
    }

    public void onDispose() {
        //throw new UnsupportedOperationException("Not supported yet.");
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

        backPan = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jGradientPanel1 = new bc.swing.comp.JGradientPanel();
        captionLabel = new javax.swing.JLabel();
        pageCaptionLabel = new javax.swing.JLabel();
        pages = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        prevButton = new bc.swing.comp.JLinkButton();
        nextButton = new bc.swing.comp.JLinkButton();
        finishButton = new bc.swing.comp.JLinkButton();

        setLayout(new java.awt.BorderLayout());

        backPan.setBackground(new java.awt.Color(255, 255, 255));
        backPan.setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jGradientPanel1.setBackground(new java.awt.Color(177, 177, 177));
        jGradientPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jGradientPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        captionLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        captionLabel.setForeground(new java.awt.Color(255, 255, 255));
        captionLabel.setText("jLabel1");
        jGradientPanel1.add(captionLabel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
        jPanel2.add(jGradientPanel1, gridBagConstraints);

        pageCaptionLabel.setFont(new java.awt.Font("Lucida Bright", 0, 12));
        pageCaptionLabel.setForeground(new java.awt.Color(153, 153, 255));
        pageCaptionLabel.setText("Page Caption");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 3, 3);
        jPanel2.add(pageCaptionLabel, gridBagConstraints);

        pages.setBackground(new java.awt.Color(255, 255, 255));
        pages.setLayout(new java.awt.CardLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel2.add(pages, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        backPan.add(jPanel2, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        prevButton.setBackground(new java.awt.Color(204, 204, 204));
        prevButton.setText("Prev");
        prevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevButtonActionPerformed(evt);
            }
        });
        jPanel3.add(prevButton);
        prevButton.getAccessibleContext().setAccessibleParent(null);

        nextButton.setBackground(new java.awt.Color(204, 204, 204));
        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });
        jPanel3.add(nextButton);
        nextButton.getAccessibleContext().setAccessibleParent(null);

        finishButton.setBackground(new java.awt.Color(204, 0, 51));
        finishButton.setText("Finish");
        finishButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishButtonActionPerformed(evt);
            }
        });
        jPanel3.add(finishButton);
        finishButton.getAccessibleContext().setAccessibleParent(null);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        backPan.add(jPanel3, gridBagConstraints);

        add(backPan, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        //model.flipNextPage();
        //((CardLayout)pages.getLayout()).next(pages);
        //currentPageIndex
        currentPageIndex = (currentPageIndex + 1)%names.size();
        onActivePageChanged();
    }//GEN-LAST:event_nextButtonActionPerformed

    private void prevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevButtonActionPerformed
        //model.flipBackPage();
        //((CardLayout)pages.getLayout()).previous(pages);
        currentPageIndex = (currentPageIndex - 1 + names.size())%names.size();
        onActivePageChanged();
    }//GEN-LAST:event_prevButtonActionPerformed

    private void finishButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishButtonActionPerformed
        page.executeAction("FINISH");
    }//GEN-LAST:event_finishButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backPan;
    private javax.swing.JLabel captionLabel;
    private bc.swing.comp.JLinkButton finishButton;
    private bc.swing.comp.JGradientPanel jGradientPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private bc.swing.comp.JLinkButton nextButton;
    private javax.swing.JLabel pageCaptionLabel;
    private javax.swing.JPanel pages;
    private bc.swing.comp.JLinkButton prevButton;
    // End of variables declaration//GEN-END:variables
}
