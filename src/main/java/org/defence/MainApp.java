package org.defence;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.defence.infrastructure.DbHelper;

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
        String fxmlFile = "/fxml/hello.fxml";
//        String fxmlFile = "/fxml/NewCharacteristic.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setTitle("JavaFX and Maven");

//        AquaFx.style();
//        AquaFx.class.getResource("mac_os.css").toExternalForm();

        final Scene scene = new Scene(root);
        scene.getStylesheets().add((getClass().getResource("/css/TreeView.css")).toExternalForm());
        scene.getStylesheets().add((getClass().getResource("/css/SplitPanel.css")).toExternalForm());
 //       scene.getStylesheets().add((getClass().getResource("/css/Button.css")).toExternalForm());
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
//                JOptionPane.showMessageDialog(null, "Exit!");
                DbHelper.terminateDbConnection();
                Platform.exit();
                System.exit(0);
            }
        });

        scene.setFill(null);
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(1000);
        stage.setResizable(true);
        stage.show();
    }
}
