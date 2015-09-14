package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.defence.viewmodels.DescriptionFormatViewModel;
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
	TreeView<DescriptionFormatViewModel> formatsTreeView;


    @InjectViewModel
    private MainViewModel viewModel;

    private void exitBtnRegisterEvents() {
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
    }

    public void exitButtonClicked(Event event) {
        viewModel.getExitCommand().execute();
    }


    public void initialize() {
//        exitBtnRegisterEvents();

        /*cancelMesTypeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
                tabs.getTabs().remove(measurementTypeTab);
            }
        });*/
//        new DescriptionFormatEditView().initializeStage();

		DescriptionFormatViewModel root = new DescriptionFormatViewModel("code", "ROOT");
		TreeItem<DescriptionFormatViewModel> rootItem = new TreeItem<>(root);

		formatsTreeView.rootProperty().bindBidirectional(new SimpleObjectProperty<>(rootItem));
		formatsTreeView.setCellFactory(new Callback<TreeView<DescriptionFormatViewModel>, TreeCell<DescriptionFormatViewModel>>() {
			@Override
			public TreeCell<DescriptionFormatViewModel> call(TreeView<DescriptionFormatViewModel> param) {
				return null;
			}
		});

		//https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm
		System.out.println("Inside main view");

//		formatsTreeView.proper
//        formatsTreeView.cellFactoryProperty().bindBidirectional(viewModel.formatsProperty());
	}
}


