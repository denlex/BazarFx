package org.defence.tests;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;

/**
 * Created by root on 8/12/15.
 */
public class NotificationTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
// Create a WebView
        WebView webView = new WebView();
        webView.setStyle("-fx-background-color: greenyellow");
        webView.setMinHeight(300);
        webView.setMinWidth(600);

        // Wrap it inside a NotificationPane
        NotificationPane notificationPane = new NotificationPane(webView);
        notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);

        // and put the NotificationPane inside a Tab
        Tab tab1 = new Tab("Tab 1");
        tab1.setContent(notificationPane);

        // and the Tab inside a TabPane. We just have one tab here, but of course
        // you can have more!
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(tab1);

        Group group = new Group();
        group.getChildren().add(tabPane);

        final Scene scene = new Scene(group, 800, 600);
        scene.setFill(null);
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(1000);
        stage.setResizable(true);
        stage.show();
    }
}
