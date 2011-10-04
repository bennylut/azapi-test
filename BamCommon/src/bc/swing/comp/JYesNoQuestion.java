/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JYesNoQuestion.java
 *
 * Created on 03/07/2011, 14:11:31
 */
package bc.swing.comp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;
import static bc.dsl.JavaDSL.*;

/**
 *
 * @author bennyl
 */
public class JYesNoQuestion extends javax.swing.JPanel implements TextualComponent {

    /** Creates new form JYesNoQuestion */
    public JYesNoQuestion() {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        y = new javax.swing.JRadioButton();
        n = new javax.swing.JRadioButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(88, 27));
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 2));

        y.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(y);
        y.setSelected(true);
        y.setText("Yes");
        add(y);

        n.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(n);
        n.setText("No");
        add(n);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton n;
    private javax.swing.JRadioButton y;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setValueText(String text) {
        if (eq(lc(text), "yes")) {
            y.setSelected(true);
        }else{
            n.setSelected(true);
        }
    }


    @Override
    public String getValueText() {
        return y.isSelected()? "yes" : "no";
    }

    @Override
    public void addValueChangedListener(final ValueChangedListener l) {
        JRadioButton[] btns = new JRadioButton[]{y,n};
        
        for (JRadioButton b : btns){
            b.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    l.onChange(JYesNoQuestion.this);
                }
            });
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        y.setEnabled(enabled);
        n.setEnabled(enabled);
    }
    
    
}
