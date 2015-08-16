package org.defence.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by root on 8/14/15.
 */
public class PropertyTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/test/PropertiesForm.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
//        CustomControl control = new CustomControl();
        stage.setTitle("JavaFX and Maven");

        final Scene scene = new Scene(root);

//        scene.setFill(null);
        stage.setScene(scene);
        stage.setWidth(600);
        stage.setHeight(500);
        stage.setMinWidth(600);
        stage.setMinHeight(500);
        stage.setResizable(true);
        stage.setMaximized(false);
        stage.show();
    }
}
