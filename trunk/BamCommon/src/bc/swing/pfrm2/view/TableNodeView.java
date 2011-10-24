/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TablePV2.java
 *
 * Created on Sep 11, 2011, 7:26:17 PM
 */
package bc.swing.pfrm2.view;

import bc.swing.comp.JActionView;
import bc.swing.models.DataExtractor;
import bc.swing.models.DataInserter;
import bc.swing.models.GenericTableModel;
import bc.swing.pfrm.Action;
import bc.swing.pfrm2.Att;
import bc.swing.pfrm2.ItemEditor;
import bc.swing.pfrm2.ItemExpander;
import bc.swing.pfrm2.Node;
import bc.swing.pfrm2.NodeView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

/**
 *
 * @author BLutati
 */
public class TableNodeView extends javax.swing.JPanel implements NodeView {

    public static final String ATT_FIRST_COLUMN_WIDTH = "FIRST COLUMN WIDTH";
    JPopupMenu pmenu;
    private GenericTableModel innerModel;

    /** Creates new form TablePV2 */
    public TableNodeView() {
        initComponents();
        pmenu = new JPopupMenu();
    }

    public DataExtractor getDataExtractor(final Node n) {
        String[] itemColumns = ((ItemExpander) n.getAtt(Att.ITEM_EXPANDER_DEF)).columns();

        DataExtractor extractort = new DataExtractor(itemColumns) {

            @Override
            public Object getData(String dataName, Object from) {
                Node nfrom = (Node) from;
                return n.getChild(nfrom.getValue()).getChild(dataName).getValue();
            }
        };
        return extractort;
    }

    public DataInserter getDataInserter(final Node n) {
        final ItemEditor editor = (ItemEditor) n.getAtt(Att.ITEM_EDITOR_DEF);
        if (editor == null) {
            return new DataInserter() {

                @Override
                public void setData(String dataName, Object from, Object newValue) {
                }
            };
        }

        String[] itemColumns = editor.columns();
        DataInserter inserter = new DataInserter(itemColumns) {

            @Override
            public void setData(String dataName, Object from, Object newValue) {
                n.getChild(((Node) from).getValue()).getChild(dataName).setValue(newValue);
            }
        };
        return inserter;
    }

    private void configureFirstColumnWidth(final Node n) {
        final int firstColumnWidth = n.getIntegerAtt(ATT_FIRST_COLUMN_WIDTH, -1);

        if (firstColumnWidth >= 0) {
            final TableColumn col0 = table.getColumnModel().getColumn(0);
            col0.setPreferredWidth(firstColumnWidth);
            col0.setMaxWidth(firstColumnWidth);
            col0.setMinWidth(firstColumnWidth);
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
        table = new javax.swing.JTable();
        globalActionsPan = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 245, 245)));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.setFillsViewportHeight(true);
        table.setGridColor(new java.awt.Color(240, 245, 255));
        table.setRowHeight(20);
        table.setShowVerticalLines(false);
        jScrollPane1.setViewportView(table);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        globalActionsPan.setBackground(new java.awt.Color(255, 255, 255));
        globalActionsPan.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        add(globalActionsPan, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel globalActionsPan;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    protected void hideTableHeader() {
        table.setTableHeader(null);
    }

    private void configurePopupMenu(Node n) {
        pmenu.removeAll();
        for (final Action a : n.actionsList()) {
            if (a.isItemAction()) {
                pmenu.add(new AbstractAction(a.getName(), a.getIcon()) {

                    public void actionPerformed(ActionEvent e) {
                        a.execute();
                    }
                });
            }
        }

        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON3) {
                    return;
                }
                int p = table.rowAtPoint(e.getPoint());
                if (p >= 0) {
                    table.getSelectionModel().setSelectionInterval(p, p);
                    pmenu.show(table, e.getX(), e.getY());
                }
            }
        });

    }

    private void configureGlobalActions(Node n) {
        globalActionsPan.setVisible(false);
        for (Action a : n.actionsList()) {
            if (!a.isItemAction()) {
                globalActionsPan.setVisible(true);
                globalActionsPan.add(new JActionView(a, Color.GRAY));
            }
        }
    }

    public void setNode(final Node node) {
        configurePopupMenu(node);
        configureGlobalActions(node);
        DataExtractor extractort = getDataExtractor(node);
        innerModel = new GenericTableModel(extractort);
        innerModel.changeInnerList(node.childrenList());

        DataInserter inserter = getDataInserter(node);
        if (inserter != null) {
            innerModel.setInserter(inserter);
        }

        table.setModel(innerModel);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (table.getSelectedRow() >= 0) {
                    final Object newSelection = innerModel.getInnerData().get(table.getSelectedRow());
                    node.putAtt(Att.SELECTION, newSelection);
                } else {
                    node.putAtt(Att.SELECTION, null);
                }

            }
        });
        configureFirstColumnWidth(node);
        table.setRowSelectionAllowed(node.getBooleanAtt(Att.ALLOW_ITEM_SELECTION, true));
    }

    public void syncFromView(Node c) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void syncToView(Node c) {
        System.err.println("TODO: CHANGES SHOULD BE HINTED SO THAT THEY WILL BE QUICKER...");
        innerModel.changeInnerList(c.childrenList());
        configureFirstColumnWidth(c);
        validate();
        repaint();
    }
}
