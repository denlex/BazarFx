package org.defence;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by root on 22.07.15.
 */
public class MainApp extends Application {
    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            try {
                FileWriter file = new FileWriter("errors.logs");
                file.write(e.getMessage());
                file.close();
            } catch (IOException e1) {
            }
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
//        String fxmlFile = "/fxml/hello.fxml";
        String fxmlFile = "/fxml/TestForm.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setTitle("JavaFX and Maven");
        final Scene scene = new Scene(root);
        scene.getStylesheets().add((getClass().getResource("/css/styles.css")).toExternalForm());
        scene.getStylesheets().add((getClass().getResource("/css/Button.css")).toExternalForm());
        scene.setFill(null);
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setResizable(true);
        stage.show();
    }
}
