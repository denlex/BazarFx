package org.defence;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * Created by root on 22.07.15.
 */
public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/hello.fxml";
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
