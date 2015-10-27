package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.defence.viewmodels.CatalogClassEditViewModel;

/**
 * Created by root on 10/27/15.
 */
public class CatalogClassEditView implements FxmlView<CatalogClassEditViewModel>, Returnable {
	@FXML
	TextField codeTextField;

	@FXML
	TextField nameTextField;

	@InjectViewModel
	CatalogClassEditViewModel viewModel;

	private Stage stage;
	private DialogResult dialogResult = DialogResult.CANCEL;

	@Override
	public DialogResult getModalResult() {
		return null;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void saveCatalogClassButtonClicked(Event event) {
		viewModel.getSaveCommand().execute();
		dialogResult = DialogResult.OK;
		stage.close();
	}

	public void cancelButtonClicked() {
		dialogResult = DialogResult.CANCEL;
		stage.close();
	}

	public void initializeStage() {
//		stage.onShownProperty().bindBidirectional(viewModel.shownWindowProperty());
	}

	public void initialize() {
		codeTextField.textProperty().bindBidirectional(viewModel.codeProperty());
		nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());
	}
}
