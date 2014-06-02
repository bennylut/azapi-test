/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.mui.app;

import bgu.dcr.az.common.exceptions.UnexpectedException;
import bgu.dcr.az.common.ui.FXUtils;
import bgu.dcr.az.conf.api.ConfigurationException;
import bgu.dcr.az.execs.exps.ExperimentProgress;
import bgu.dcr.az.execs.exps.ModularExperiment;
import bgu.dcr.az.mui.RootController;
import bgu.dcr.az.mui.modules.StatusSyncer;
import bgu.dcr.az.mui.scr.main.MainPage;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author bennyl
 */
public class NewFXMain extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            RootController root = new RootController();

            ModularExperiment exp = ModularExperiment.createDefault(getClass().getResourceAsStream("test.xml"), true);
            ExperimentProgress progress = exp.execute();

            root.install(ModularExperiment.class, exp);
            root.install(StatusSyncer.class, new StatusSyncer(5, progress));

            MainPage p = root.findAndInstall("main");

            root.loadView();
            Scene scene = new Scene(p.getView());

            FXUtils.startCSSLiveReloader(p.getView(), "/home/bennyl/Desktop/MoreSpace/Projects/AgentZero/mavenized-v2/AZModularUI/src/main/java/bgu/dcr/az/mui/scr/AgentZero.css");

            primaryStage.setTitle("Agent Zero Testing Session");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.setOnCloseRequest(e -> System.exit(0));
            primaryStage.show();
        } catch (ConfigurationException ex) {
            throw new UnexpectedException(ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
