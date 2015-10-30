package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
		if (typeComboBox.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setContentText("Не выбран тип организации");
			alert.showAndWait();
			return;
		}

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
//		typeComboBox.getSelectionModel().select(0);

		typeComboBox.valueProperty().bindBidirectional(viewModel.typeProperty());

		typeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			viewModel.typeProperty().unbind();
			viewModel.typeProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
		});
	}

	public void initialize() {
		initializeTypeComboBox();
		codeTextField.textProperty().bindBidirectional(viewModel.codeProperty());
		nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());
	}
}
