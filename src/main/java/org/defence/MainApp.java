package org.defence;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.defence.domain.entities.AssertedName;
import org.defence.infrastructure.UnitOfWork;

/**
 * Created by root on 22.07.15.
 */
public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);

        AssertedName assertedName = new AssertedName();
        assertedName.setName("Hernya");
        assertedName.setNumber("Bolshaya");
        UnitOfWork unitOfWork = new UnitOfWork(true);
        unitOfWork.assertedNameRepository.insert(assertedName);
        unitOfWork.save();
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/hello.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setTitle("JavaFX and Maven");
        final Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add((getClass().getResource("/css/styles.css")).toExternalForm());
        scene.getStylesheets().add((getClass().getResource("/css/Button.css")).toExternalForm());
        scene.setFill(null);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
