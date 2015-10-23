package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.defence.viewmodels.OrganizationEditViewModel;

/**
 * Created by root on 10/22/15.
 */
public class OrganizationEditView implements FxmlView<OrganizationEditViewModel>, Returnable {
	@FXML
	TextField codeTextField;

	@FXML
	TextField nameTextField;

	@FXML
	ComboBox<String> typeComboBox;

	@InjectViewModel
	OrganizationEditViewModel viewModel;

	private Stage stage;
	private DialogResult dialogResult = DialogResult.CANCEL;

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

	public void initializeTypeComboBox() {
		typeComboBox.getItems().add("Организация-разработчик");
		typeComboBox.getItems().add("Организация-исполнитель");
		typeComboBox.getSelectionModel().select(0);
	}

	public void initialize() {
		initializeTypeComboBox();
		codeTextField.textProperty().bindBidirectional(viewModel.codeProperty());
		nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());
	}
}
