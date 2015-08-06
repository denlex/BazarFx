package org.defence.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by root on 22.07.15.
 */
public class MainController {
    @FXML
    private Button btn;
    Group root = new Group();

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
}


