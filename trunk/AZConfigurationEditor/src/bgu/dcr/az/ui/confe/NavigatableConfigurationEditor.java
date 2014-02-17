/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.ui.confe;

import bgu.dcr.az.anop.conf.Configuration;
import bgu.dcr.az.anop.conf.Property;
import bgu.dcr.az.anop.conf.impl.FromCollectionPropertyValue;
import bgu.dcr.az.anop.conf.impl.FromConfigurationPropertyValue;
import bgu.dcr.az.anop.conf.impl.FromStringPropertyValue;
import bgu.dcr.az.anop.reg.RegisteryUtils;
import bgu.dcr.az.anop.utils.PropertyUtils;
import bgu.dcr.az.ui.AppController;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Zovadi
 */
public class NavigatableConfigurationEditor extends BorderPane implements PropertyEditor {

    private ConfigurationEditor configurationEditor;
    private ConfigurationPropertyEditor configurationPropertyEditor;
    private CollectionPropertyEditor collectionPropertyEditor;
    private TerminalPropertyEditor terminalPropertyEditor;
    private final ScrollPane scrollPane;
    private final TreeView configurationTree;

    private Configuration configuration;
    private boolean readOnly;
    private boolean updateSelectionAllowed = true;

    private Map<Object, TreeItem> treeNodes;

    public NavigatableConfigurationEditor() {
        configurationEditor = new ConfigurationEditor(this);
        configurationPropertyEditor = new ConfigurationPropertyEditor(this, false);
        collectionPropertyEditor = new CollectionPropertyEditor(this);
        terminalPropertyEditor = new TerminalPropertyEditor();

        configurationTree = new TreeView();
        configurationTree.setShowRoot(true);
        configurationTree.setMinWidth(250);
        configurationTree.setPrefWidth(250);
        configurationTree.setMaxWidth(250);
        configurationTree.getSelectionModel().selectedItemProperty().addListener((p, o, n) -> {
            if (updateSelectionAllowed) {
                updateSelectionAllowed = false;
                navigateToSelectedItem();
                updateSelectionAllowed = true;
            }
        });
        setLeft(configurationTree);

        scrollPane = new ScrollPane(configurationEditor);
        scrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        scrollPane.setFitToWidth(true);

//        scrollPane.setStyle("-fx-border: 10px solid; -fx-border-color: red;");
        setCenter(scrollPane);
        scrollPane.getStyleClass().add("conf-editor");

        treeNodes = new HashMap<>();
//        setStyle("-fx-border: 10px solid; -fx-border-color: red;");
    }

    private void navigateToSelectedItem() {
        Object item = configurationTree.getSelectionModel().getSelectedItem();
        System.out.println("navigating to " + item);
        if (item != null) {
            TreeItemProperty p = (TreeItemProperty) ((TreeItem) item).getValue();
            if (p.getConfiguration() != null) {
                configurationEditor = new ConfigurationEditor(this);
                configurationEditor.setModel(p.getConfiguration(), readOnly);
                scrollPane.setContent(configurationEditor);
            }
            if (p.getProperty() != null) {
                final Property property = p.getProperty();
                if (PropertyUtils.isPrimitive(property)) {
                    terminalPropertyEditor = new TerminalPropertyEditor();
                    terminalPropertyEditor.setModel(p.getProperty(), readOnly);
                    scrollPane.setContent(terminalPropertyEditor);
                } else if (PropertyUtils.isCollection(property)) {
//                    removeChildren(property);
                    collectionPropertyEditor = new CollectionPropertyEditor(this);
                    collectionPropertyEditor.setModel(property, readOnly);
                    collectionPropertyEditor.setExpanded(true);
                    scrollPane.setContent(collectionPropertyEditor);
                } else {
                    configurationPropertyEditor = new ConfigurationPropertyEditor(this, false);
                    configurationPropertyEditor.setModel(property, readOnly);
                    configurationPropertyEditor.setExpanded(true);
                    scrollPane.setContent(configurationPropertyEditor);
                }
            }
        }
    }

    @Override

    public void setModel(Configuration configuration, boolean readOnly) {
        this.readOnly = readOnly;
        this.configuration = configuration;
        configurationEditor.setModel(configuration, readOnly);

        buildTree();
        scrollPane.setContent(configurationEditor);
    }

    @Override
    public void setModel(Property property, boolean readOnly) {
    }

    @Override
    public Property getModel() {
        return null;
    }

    private void buildTree() {
        System.out.println("rebuilding tree... ");
        treeNodes = new HashMap<>();

        TreeItem root = new TreeItem(new TreeItemProperty(configuration, configuration.registeredName()));
        root.setExpanded(true);
        configurationTree.setRoot(root);
        treeNodes.put(configuration, root);

        LinkedList open = new LinkedList();
        open.add(configuration);

        fillTreeNodes(open);
    }

    private void fillTreeNodes(LinkedList open) {
        while (!open.isEmpty()) {
            Object parent = open.remove();

            if (parent instanceof Configuration) {
                addFromConfigurationTreeNodes((Configuration) parent, parent, open);
            } else {
                Property prop = (Property) parent;
                if (prop.get() == null) {
                    continue;
                }
                if (prop.get() instanceof FromConfigurationPropertyValue) {
                    addFromConfigurationTreeNodes(((FromConfigurationPropertyValue) prop.get()).getValue(), parent, open);
                }
                if (prop.get() instanceof FromCollectionPropertyValue) {
                    addLeafTreeNode(parent, prop, "Collection of " + prop.name());
                }
                if (prop.get() instanceof FromStringPropertyValue) {
                    addLeafTreeNode(parent, prop, prop.name());
                }
            }
        }
    }

    private void addFromConfigurationTreeNodes(Configuration conf, Object parent, LinkedList open) {
        for (Property property : conf.properties()) {
            if (PropertyUtils.isPrimitive(property)) {
//                addLeafTreeNode(parent, property, property.name(), open);
            } else if (PropertyUtils.isCollection(property)) {
                addLeafTreeNode(parent, property, "Collection of " + property.name());
            } else {
                addConfigurationTreeNode(parent, property, property.name(), open);
            }
        }
    }

    private void addLeafTreeNode(Object parent, Property child, String treeName) {
        if (child.doc() == null || !"false".equals(child.doc().first("UIVisibility"))) {
            TreeItem item = new TreeItem(new TreeItemProperty(child, treeName));
            item.setExpanded(true);
            treeNodes.get(parent).getChildren().add(item);
            treeNodes.put(child, item);
            Label infoContainer = new Label("");
            PropertyEditor.updateInfo(infoContainer, child);
            item.setGraphic(infoContainer);
        }
    }

    private void addConfigurationTreeNode(Object parent, Property child, String treeName, LinkedList open) {
        addLeafTreeNode(parent, child, treeName);
        open.add(child);
    }

    private static class TreeItemProperty {

        private final Configuration configuration;
        private final Property property;
        private final String name;

        public TreeItemProperty(Configuration configuration, String name) {
            this.configuration = configuration;
            this.property = null;
            this.name = name;
        }

        public TreeItemProperty(Property property, String name) {
            this.configuration = null;
            this.property = property;
            this.name = name;
        }

        public Configuration getConfiguration() {
            return configuration;
        }

        public Property getProperty() {
            return property;
        }

        @Override
        public String toString() {
            if (property != null && property.get() != null && property.get() instanceof FromConfigurationPropertyValue) {
                FromConfigurationPropertyValue fcpv = (FromConfigurationPropertyValue) property.get();
                Property confName = fcpv.getValue().get("name") == null ? fcpv.getValue().get("Name") : fcpv.getValue().get("name");
                if (confName != null && confName.get() != null) {
                    return name + " [" + confName.get().stringValue() + "]";
                }
            }
            return name;
        }
    }

    public void removeSubTree(Property root) {
        if (!treeNodes.keySet().contains(root)) {
            return;
        }

        doWhilePreserveSelection(() -> {
            removeChildren(root);

            TreeItem node = treeNodes.get(root);

            if (node.getParent() != null) {
                node.getParent().getChildren().remove(node);
            }

            treeNodes.remove(root);
        });
    }

    private void doWhilePreserveSelection(Runnable r) {
        TreeItem oldSelection = (TreeItem) configurationTree.getSelectionModel().getSelectedItem();

        r.run();

        System.out.println("going back to old selection: " + oldSelection);

        boolean old = updateSelectionAllowed;
        updateSelectionAllowed = false;

        configurationTree.getSelectionModel().select(oldSelection);
        if (oldSelection != null) {
            final int row = configurationTree.getRow(oldSelection);
            System.out.println("and focusing: " + row);
            configurationTree.getFocusModel().focus(row + 1);
        }

        updateSelectionAllowed = old;

    }

    public void removeChildren(Property root) {
        System.out.println("removing children " + root);

        if (!treeNodes.keySet().contains(root)) {
            return;
        }

        TreeItem rootNode = treeNodes.get(root);

        doWhilePreserveSelection(() -> {

            LinkedList open = new LinkedList();
            open.add(rootNode);

            while (!open.isEmpty()) {
                TreeItem parentNode = (TreeItem) open.remove();
                for (Object node : parentNode.getChildren().toArray()) {
                    parentNode.getChildren().remove(node);
                    treeNodes.remove(((TreeItemProperty) ((TreeItem) node).getValue()).getProperty());
                    open.add(node);
                }
            }
        });

    }

    public void addFromConfigurationTreeNodes(Property property) {
        if (property == null || property.get() == null) {
            return;
        }
        TreeItem root = treeNodes.get(property);

        LinkedList open = new LinkedList();

        addFromConfigurationTreeNodes(((FromConfigurationPropertyValue) property.get()).getValue(), property, open);

        fillTreeNodes(open);
    }

    public void addCollectionItemTreeNode(Property collection, Property item) {
        if (item == null || item.get() == null) {
            return;
        }

        removeSubTree(item);
        LinkedList open = new LinkedList();

        if (item.get() instanceof FromConfigurationPropertyValue) {
            String typeName = "item";
            if (RegisteryUtils.getDefaultRegistery().getImplementors(collection.typeInfo().getGenericParameters().get(0).getType()).size() == 1) {
                Class type = item.typeInfo().getType();
                typeName = RegisteryUtils.getDefaultRegistery().getRegisteredClassName(type);
            }
            addLeafTreeNode(collection, item, typeName);
            addFromConfigurationTreeNodes(((FromConfigurationPropertyValue) item.get()).getValue(), item, open);
        }

        if (item.get() instanceof FromCollectionPropertyValue) {
            addLeafTreeNode(collection, item, "item");
        }

        if (item.get() instanceof FromStringPropertyValue) {
            addLeafTreeNode(collection, item, "item");
        }

        fillTreeNodes(open);
    }

}