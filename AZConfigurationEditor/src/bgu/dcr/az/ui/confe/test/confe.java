/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.ui.confe.test;

import bgu.dcr.az.anop.conf.Configuration;
import bgu.dcr.az.anop.conf.ConfigurationException;
import bgu.dcr.az.anop.conf.ConfigurationUtils;
import bgu.dcr.az.ui.confe.ConfigurationEditor;
import bgu.dcr.az.ui.confe.TitledPaneTreeNavigator;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import test.delete.me.SomeClass;

/**
 *
 * @author Shl
 */
public class confe extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SomeClass c = new SomeClass();
        c.setC('~');
        c.setJ("bla bla benjamin button");
        c.setK(true);
        c.setE(SomeClass.E.MEH);
        SomeClass.ExtendedSomeType2 inside = new SomeClass.ExtendedSomeType2();
        inside.setI(2);
        inside.setX(4);
        inside.setOther(new SomeClass.ExtendedSomeType1());
        c.setComplex(inside);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("benny");
        arrayList.add("vadim");
        arrayList.add("clint eastwood");
        c.setNames(arrayList);
        final Configuration conf = ConfigurationUtils.read(getClass().getResourceAsStream("test.xml"));//ConfigurationUtils.load(c);

//        NavigatableConfigurationEditor editor = new NavigatableConfigurationEditor();
        ConfigurationEditor editor = new ConfigurationEditor();
        editor.setModel(conf, false, null);
//        editor.setModel(ConfigurationUtils.get(CPExperiment.class), false);

        TitledPaneTreeNavigator navigator = new TitledPaneTreeNavigator(editor);
        Button button = new Button(":/");
        button.setOnAction((ActionEvent event) -> {
            try {
                System.out.println("" + ConfigurationUtils.toXML(conf).toXML());
            } catch (ConfigurationException ex) {
                Logger.getLogger(confe.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        navigator.getChildren().add(button);

        Scene scene = new Scene(navigator);
        
//        scene.getStylesheets().add(getClass().getResource("ceditor.css").toExternalForm());

        //DO NOT DELETE BELLOW THIS LINE PLEASE!
        scene.getStylesheets().add("file:///C:/Users/User/Desktop/Projects/AgentZero/trunk/AZConfigurationEditor/src/bgu/dcr/az/ui/confe/ceditor.css");
//        ScenicView.show(scene);
//        TimingUtils.scheduleRepeating(() -> FXUtils.reloadSceneStylesheet(scene), 1000);
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}