package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.defence.viewmodels.CatalogDescriptionEditViewModel;
import org.defence.viewmodels.CharacteristicValueViewModel;

/**
 * Created by root on 9/25/15.
 */
public class CatalogDescriptionEditView implements FxmlView<CatalogDescriptionEditViewModel>, Returnable {

	@FXML
	TextField codeTextField;

	@FXML
	TextField nameTextField;

	@FXML
	TableView<CharacteristicValueViewModel> valuesTableView;

	@FXML
	TableColumn<CharacteristicValueViewModel, String> characteristicCodeTableColumn;

	@FXML
	TableColumn<CharacteristicValueViewModel, String> characteristicTableColumn;

	@FXML
	TableColumn<CharacteristicValueViewModel, String> valueTableColumn;

	@FXML
	TableColumn<CharacteristicValueViewModel, String> measurementTableColumn;

	@InjectViewModel
	CatalogDescriptionEditViewModel viewModel;

	Stage stage;
	DialogResult dialogResult = DialogResult.CANCEL;

	private void initializeValuesTableView() {
		valuesTableView.setEditable(true);
		valuesTableView.itemsProperty().bindBidirectional(viewModel.valuesProperty());

		characteristicCodeTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
				.getCharacteristic().getCode()));

		characteristicTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
				.getCharacteristic().getName()));

		valueTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue()));
		valueTableColumn.setCellFactory(param -> new EditableTableCell());
		valueTableColumn.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow
				()).setValue(event.getNewValue()));

		measurementTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
				.getCharacteristic().getMeasurementText()));

		valuesTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
				newValue) -> {
			viewModel.selectedCharacteristicValueProperty().unbind();
			viewModel.selectedCharacteristicValueProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
		});
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public DialogResult getModalResult() {
		return dialogResult;
	}

	public void initializeStage() {
		stage.onShownProperty().bindBidirectional(viewModel.shownWindowProperty());
	}

	public void saveButtonClicked(Event event) {
		viewModel.getSaveCommand().execute();
		dialogResult = DialogResult.OK;
		stage.close();
	}

	public void cancelButtonClicked(Event event) {
		dialogResult = DialogResult.CANCEL;
		stage.close();
	}

	public void initialize() {
		codeTextField.textProperty().bindBidirectional(viewModel.codeProperty());
		nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());

		initializeValuesTableView();
	}

	// EditableTableCell - for editing capability in a TableCell
	public static class EditableTableCell extends TableCell<CharacteristicValueViewModel, String> {
		private TextField textField;

		public EditableTableCell() {
		}

		@Override
		public void startEdit() {
			super.startEdit();

			if (textField == null) {
				createTextField();
			}
			setText(null);
			setGraphic(textField);
			textField.selectAll();
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();
			setText(getItem());
			setGraphic(null);
		}

		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (textField != null) {
						textField.setText(getString());
					}
					setText(null);
					setGraphic(textField);
				} else {
					setText(getString());
					setGraphic(null);
				}
			}
		}

		private void createTextField() {
			textField = new TextField(getString());
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
			textField.setOnKeyReleased(t -> {
				if (t.getCode() == KeyCode.ENTER) {
					commitEdit(textField.getText());
					System.out.println(getText());
				} else if (t.getCode() == KeyCode.ESCAPE) {
					cancelEdit();
				}
			});
		}

		private String getString() {
			return getItem() == null ? "" : getItem().toString();
		}
	}
}
