package org.defence;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.defence.infrastructure.DbHelper;
import org.defence.viewmodels.MainViewModel;
import org.defence.views.MainView;

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
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;

        primaryStage.setTitle("Library JavaFX");
/*        primaryStage.setMinWidth(1200);
        primaryStage.setMaxWidth(1200);*/
        primaryStage.setMinHeight(700);

        ViewTuple<MainView, MainViewModel> viewTuple = FluentViewLoader.fxmlView(MainView.class).load();

        Parent root = viewTuple.getView();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        scene.getStylesheets().add((getClass().getResource("/css/TreeView.css")).toExternalForm());
        scene.getStylesheets().add((getClass().getResource("/css/SplitPanel.css")).toExternalForm());

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
//                JOptionPane.showMessageDialog(null, "Exit!");
                DbHelper.terminateDbConnection();
                Platform.exit();
                System.exit(0);
            }
        });

        scene.setFill(null);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(700);
        primaryStage.setResizable(true);
        primaryStage.show();

//        FlatterFX.style(FlatterInputType.DEFAULT);
    }

    public static Stage mainStage;
}
