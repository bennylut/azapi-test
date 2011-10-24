/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ListPV.java
 *
 * Created on 11/09/2011, 15:13:09
 */
package bc.swing.pfrm.views;

import bc.swing.dnd.ObjectTransferHandler;
import bc.swing.models.GenericListModel;
import bc.swing.pfrm.Action;
import bc.swing.pfrm.ano.ViewHints.DND;
import bc.swing.pfrm.DeltaHint;
import bc.swing.pfrm.BaseParamModel;
import bc.swing.pfrm.FieldParamModel.ChangeListener;
import bc.swing.pfrm.ParamView;
import bc.swing.renderers.DefaultListRenderer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author bennyl
 */
public class ListPV extends javax.swing.JPanel implements ParamView {

    private GenericListModel innerModel;
    JPopupMenu pmenu;

    /** Creates new form ListPV */
    public ListPV() {
        initComponents();
        pmenu = new JPopupMenu();

        lst.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                for (FocusListener l : getFocusListeners()) {
                    l.focusGained(e);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                for (FocusListener l : getFocusListeners()) {
                    l.focusLost(e);
                }
            }
        });
    }

    private void configurePopupMenu(BaseParamModel model) {
        pmenu.removeAll();
        for (final Action a : model.getActions()) {
            pmenu.add(new AbstractAction(a.getName(), a.getIcon()) {

                public void actionPerformed(ActionEvent e) {
                    a.execute();
                }
            });
        }

        lst.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON3) {
                    return;
                }
                int p = lst.locationToIndex(e.getPoint());
                if (p >= 0) {
                    lst.getSelectionModel().setSelectionInterval(p, p);
                    pmenu.show(lst, e.getX(), e.getY());
                }
            }
        });

    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        if (lst != null) {
            lst.setBackground(bg);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        lst = new javax.swing.JList();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);

        lst.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));
        lst.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lst);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lst;
    // End of variables declaration//GEN-END:variables

    public void setParam(final BaseParamModel param) {
        configurePopupMenu(param);
        innerModel = new GenericListModel();

        List val = (List) param.getValue();

        innerModel.fillWith(val);
        lst.setModel(innerModel);

        lst.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                param.fireSelectionChanged(lst.getSelectedValue());
            }
        });

        if (!param.getViewHints().dnd().equals(DND.UNDEF)) {
            setDND(param);
        }

        if (param.getViewHints().allowDeleteSelection()) {
            allowDeleteSelection();
        }

        final DefaultListRenderer renderer = new DefaultListRenderer();
        renderer.setModel(param);
        lst.setCellRenderer(renderer);
    }

    public void reflectChangesToParam(BaseParamModel to) {
        List tol = (List) to.getValue();
        tol.clear();
        tol.addAll(innerModel.getInnerList());
    }

    public void onChange(BaseParamModel source, Object newValue, Object deltaHint) {
        if (deltaHint != null && deltaHint instanceof DeltaHint) {
            DeltaHint delta = (DeltaHint) deltaHint;
            List nval = (List) newValue;
            switch (delta.type) {
                case DeltaHint.LAST_ITEM_ADDED_TYPE:
                    innerModel.addLast(nval.get(nval.size() - 1));
                    break;
                case DeltaHint.ONE_ITEM_REOMVED_TYPE:
                    innerModel.remove(delta.item);
                    break;
                case DeltaHint.ONE_ITEM_CHANGED_TYPE:
                    innerModel.fireItemChanged(delta.item);
                    break;
            }
        } else if (deltaHint == null) {
            innerModel.clear();
            innerModel.fillWith((List) newValue);
//            lst.setModel(innerModel);
        }
    }

    private void setDND(final BaseParamModel pmodel) {
        if (pmodel.getViewHints().dnd().equals(DND.DROP)) {

            lst.setDropMode(DropMode.ON_OR_INSERT);
            lst.setTransferHandler(new ObjectTransferHandler(ObjectTransferHandler.DragSupport.MOVE, true, false) {

                @Override
                protected void exportDone(Object data, JComponent source, int action) {
                }

                @Override
                public boolean importData(Object data, TransferSupport info) {
                    try {
                        JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
                        int index = dl.getIndex();

                        if (index < 0) {
                            return false;
                        }

                        GenericListModel model = (GenericListModel) lst.getModel();
                        model.add(index, pmodel.dropFilter(data));

                        return true;

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return false;
                    }
                }

                @Override
                public Object exportData(JComponent c) {
                    return null;
                }
            });

        }
    }

    private void allowDeleteSelection() {
        lst.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    Object[] selected = lst.getSelectedValues();
                    for (Object s : selected) {
                        innerModel.remove(s);
                    }
                }
            }
        });


    }
}