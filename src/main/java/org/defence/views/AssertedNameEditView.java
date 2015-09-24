package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.defence.viewmodels.AssertedNameEditViewModel;

/**
 * Created by root on 9/16/15.
 */
public class AssertedNameEditView implements FxmlView<AssertedNameEditViewModel>, Returnable {
	@FXML
	TextField codeTextField;

	@FXML
	TextField nameTextField;

	@InjectViewModel
	AssertedNameEditViewModel viewModel;

	Stage stage;
	DialogResult dialogResult = DialogResult.CANCEL;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public DialogResult getModalResult() {
		return dialogResult;
	}

	public void saveButtonClicked() {
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
