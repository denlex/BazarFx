package org.defence;

import javafx.application.Application;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Created by root on 23.07.15.
 */
public class TestApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        WebView browser = new WebView();
//        WebEngine webEngine = new WebEngine();
        WebEngine webEngine = browser.getEngine();

        /*webEngine.getLoadWorker()
                .stateProperty()
                .addListener((obs, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        stage.setTitle(webEngine.getLocation());
                    }
                }); // addListener()

// begin loading...*/
        webEngine.load("http://www.oracle.com");
        stage.setMinHeight(400);
        stage.show();
    }
}
