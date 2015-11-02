package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.defence.viewmodels.CatalogDescriptionEditViewModel;
import org.defence.viewmodels.CharacteristicValueViewModel;
import org.defence.viewmodels.OrganizationViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

	@FXML
	ComboBox<OrganizationViewModel> organizationComboBox;

	@FXML
	TextField applicationNumberTextField;

	@FXML
	TextField registrationNumberTextField;

	@FXML
	DatePicker registrationDateDatePicker;

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

		if (!viewModel.getSaveCommandSuccess()) {
			return;
		}

		dialogResult = DialogResult.OK;
		stage.close();
	}

	public void cancelButtonClicked(Event event) {
		dialogResult = DialogResult.CANCEL;
		stage.close();
	}

	public void initializeDatePicker() {
		final String pattern = "dd.MM.yyyy";

		StringConverter converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter =
					DateTimeFormatter.ofPattern(pattern);

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		};
		registrationDateDatePicker.setConverter(converter);
	}

	public void initialize() {
		initializeDatePicker();

		organizationComboBox.itemsProperty().bindBidirectional(viewModel.organizationsProperty());

		organizationComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
				-> {
			viewModel.selectedOrganizationProperty().unbind();
			viewModel.selectedOrganizationProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
		});


		organizationComboBox.valueProperty().bindBidirectional(viewModel.organizationProperty());

		codeTextField.textProperty().bindBidirectional(viewModel.codeProperty());
		nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());

		/*Bindings.bindBidirectional(applicationNumberTextField.textProperty(), viewModel.registrationInfoProperty(),
				new StringConverter<RegistrationInfoViewModel>() {

					@Override
					public String toString(RegistrationInfoViewModel object) {
						return object.getApplicationNumber();
					}

					@Override
					public RegistrationInfoViewModel fromString(String string) {
						viewModel.getRegistrationInfo().setApplicationNumber(string);
						return new RegistrationInfoViewModel(viewModel.getRegistrationInfo().toModel());
					}
				});

		Bindings.bindBidirectional(registrationNumberTextField.textProperty(), viewModel.registrationInfoProperty(),
				new StringConverter<RegistrationInfoViewModel>() {

					@Override
					public String toString(RegistrationInfoViewModel object) {
						return object.getRegistrationNumber();
					}

					@Override
					public RegistrationInfoViewModel fromString(String string) {
						viewModel.getRegistrationInfo().setRegistrationNumber(string);
						return new RegistrationInfoViewModel(viewModel.getRegistrationInfo().toModel());
					}
				});

		Bindings.bindBidirectional(registrationDateDatePicker.valueProperty(), viewModel.testDateProperty());*/

		applicationNumberTextField.textProperty().bindBidirectional(viewModel.applicationNumberProperty());
		registrationNumberTextField.textProperty().bindBidirectional(viewModel.registrationNumberProperty());
		registrationDateDatePicker.valueProperty().bindBidirectional(viewModel.registrationDateProperty());

		/*registrationDateDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {

			Instant instant = Instant.from(newValue.atStartOfDay(ZoneId.systemDefault()));
			Date date = Date.from(instant);
			viewModel.getRegistrationInfo().registrationDateProperty().bindBidirectional(new SimpleObjectProperty<>
					(date));
		});*/

		initializeValuesTableView();
	}

	public void testButtonClicked() {
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
