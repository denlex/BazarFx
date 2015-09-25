package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.defence.viewmodels.CatalogDescriptionEditViewModel;

/**
 * Created by root on 9/25/15.
 */
public class CatalogDescriptionEditView implements FxmlView<CatalogDescriptionEditViewModel>, Returnable {

	@FXML
	TextField codeTextField;

	@FXML
	TextField nameTextField;

	@InjectViewModel
	CatalogDescriptionEditView viewModel;

	Stage stage;
	DialogResult dialogResult = DialogResult.CANCEL;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public DialogResult getModalResult() {
		return dialogResult;
	}

	public void initialize() {
		codeTextField.textProperty().bindBidirectional(viewModel.codeTextField.textProperty());
		nameTextField.textProperty().bindBidirectional(viewModel.nameTextField.textProperty());
	}
}
