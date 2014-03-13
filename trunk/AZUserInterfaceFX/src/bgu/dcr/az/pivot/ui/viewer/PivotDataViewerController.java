/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.pivot.ui.viewer;

import bgu.dcr.az.common.ui.FXUtils;
import bgu.dcr.az.pivot.model.TableData;
import bgu.dcr.az.pivot.model.impl.AbstractPivot;
import bgu.dcr.az.pivot.model.impl.PlottableTableData;
import bgu.dcr.az.pivot.ui.PivotDataTableView;
import bgu.dcr.az.pivot.ui.PivotViewController;
import bgu.dcr.az.ui.screens.dialogs.Notification;
import bgu.dcr.az.ui.screens.statistics.StatisticsPlotter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Zovadi
 */
public class PivotDataViewerController implements Initializable {

    @FXML
    BorderPane pivotContainer;

    @FXML
    ScrollPane vizualizersPreviewContainerParent;

    @FXML
    HBox vizualizersPreviewContainer;

    @FXML
    BorderPane vizualizerContainer;

    @FXML
    ToggleButton tableButton;
    @FXML
    ToggleButton lineChartButton;
    @FXML
    ToggleButton barChartButton;
    @FXML
    ToggleButton pieChartButton;

    private ToggleGroup chartButtonsGroup;
    private AbstractPivot model;
    private PivotViewController pivotController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXUtils.PaneWithCTL<PivotViewController> p = FXUtils.loadFXML(PivotViewController.class, "PivotViewFXML.fxml");
        pivotController = p.getController();

        pivotContainer.setCenter(p.getPane());

        vizualizersPreviewContainerParent.setFitToHeight(true);
        vizualizersPreviewContainerParent.setFitToWidth(true);

        chartButtonsGroup = new ToggleGroup();
        tableButton.setToggleGroup(chartButtonsGroup);
        lineChartButton.setToggleGroup(chartButtonsGroup);
        barChartButton.setToggleGroup(chartButtonsGroup);
        pieChartButton.setToggleGroup(chartButtonsGroup);
        chartButtonsGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) -> updateViewModel());
        chartButtonsGroup.selectToggle(tableButton);
    }

    public void setModel(AbstractPivot model) {
        this.model = model;
        pivotController.setModel(model);
        if (model != null) {
            model.getListeners().add(pivot -> updateViewModel());
            updateViewModel();
        } else {
            setChartModel(null);
        }
    }

    private void updateViewModel() {
        if (model != null) {
            Service<TableData> service = model.getPivotedDataService();
            service.setOnSucceeded((WorkerStateEvent e) -> setChartModel((TableData) e.getSource().getValue()));
            service.setOnCancelled((WorkerStateEvent e) -> Notification.info("Execution Canceled."));
            service.start();
        }
    }

    private void setChartModel(TableData data) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (chartButtonsGroup.getSelectedToggle() == tableButton) {
                    PivotDataTableView table = new PivotDataTableView(true);
                    table.setModel(data);
                    vizualizerContainer.setCenter(table);
                    return;
                }
                StatisticsPlotter chart = new StatisticsPlotter(vizualizerContainer);
                if (chartButtonsGroup.getSelectedToggle() == lineChartButton) {
                    PlottableTableData pd = new PlottableTableData(data);
                    chart.plotLineChart(pd, pd.columns()[1].name(), pd.columns()[2].name(), pd.columns()[0].name());
//            chart.plotLineChart(data, data.columns()[0].name(), data.columns()[1].name(), data.columns()[2].name(), "Pivot chart", "Categories", "Value");
                }
                if (chartButtonsGroup.getSelectedToggle() == barChartButton) {
//            chart.plotBarChart(data, data.columns()[0].name(), data.columns()[1].name(), data.columns()[2].name(), "Pivot chart", "Categories", "Value");
                }
                if (chartButtonsGroup.getSelectedToggle() == pieChartButton) {
//            chart.plotPieChart(data, data.columns()[0].name(), data.columns()[1].name(), data.columns()[2].name(), "Pivot chart", "Categories", "Value");
                }
            }
        });
    }

}
