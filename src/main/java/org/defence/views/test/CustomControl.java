package org.defence.views.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class CustomControl extends GridPane {

    @FXML
    private Form1 form1;

    @FXML
    private Form2 form2;

    public CustomControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/test/custom_form.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        setStyle("-fx-background-color: tomato");
        try {
            this.getChildren().add(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Created by root on 16.08.15.
     */
    public class Form1 extends GridPane {
        public Form1() {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/test/Form1.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);

            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    /**
     * Created by root on 16.08.15.
     */
    public class Form2 extends GridPane {
        public Form2() {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/test/Form2.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);

            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}
