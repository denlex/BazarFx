package org.defence.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by root on 22.07.15.
 */
public class MainController implements Initializable {
    @FXML
    private MenuItem exitBtn;

    @FXML
    private Button btn;
    Group root = new Group();

    private void exitBtnRegisterEvents() {
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
    }

    @FXML
    public void onClickMethod() {
        btn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
            public void handle(ActionEvent t)  {
                String fxmlFile = "/fxml/Form.fxml";
                FXMLLoader loader = new FXMLLoader();
                Parent root = null;
                try {
                    root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final Stage stage = new Stage();
                //create root node of scene, i.e. group
                //create scene with set width, height and color
                Scene scene = new Scene(root, 200, 200, Color.WHITESMOKE);
                //set scene to stage
                stage.setScene(scene);
                //center stage on screen
                stage.centerOnScreen();
                //show the stage
                stage.show();
                //add some node to scene

            }
        });

//        root.getChildren().add(btn);
    }



    public void initialize(URL location, ResourceBundle resources) {
        exitBtnRegisterEvents();
    }
}


