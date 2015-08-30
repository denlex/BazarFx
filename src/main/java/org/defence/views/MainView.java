package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.defence.viewmodels.MainViewModel;

/**
 * Created by root on 22.07.15.
 */
public class MainView implements FxmlView<MainViewModel> {
    @FXML
    private MenuItem exitBtn;

    @FXML
    TabPane tabs;

    @FXML
    Tab measurementTypeTab;

    @FXML
    private Button cancelMesTypeBtn;

    @InjectViewModel
    private MainViewModel viewModel;

    private void exitBtnRegisterEvents() {
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
    }

    public void initialize() {
//        exitBtnRegisterEvents();

        /*cancelMesTypeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tabs.getTabs().remove(measurementTypeTab);
            }
        });*/
    }
}


