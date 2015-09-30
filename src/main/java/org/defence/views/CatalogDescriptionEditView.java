package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.defence.viewmodels.CatalogDescriptionEditViewModel;
import org.defence.viewmodels.CharacteristicValueViewModel;

/**
 * Created by root on 9/25/15.
 */
public class CatalogDescriptionEditView implements FxmlView<CatalogDescriptionEditViewModel>, Returnable {

	/*@FXML
	TextField codeTextField;*/

	@FXML
	TextField nameTextField;

	@FXML
	TableView<CharacteristicValueViewModel> valuesTableView;

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
		characteristicTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
				.getCharacteristic().getName()));

/*		//Set cell factory for cells that allow editing
		Callback<CharacteristicValueViewModel, String> cellFactory = new Callback<CharacteristicValueViewModel,
		String>() {
			@Override
			public String call(CharacteristicValueViewModel param) {
				return new EditingCell();
			}
		};*/
		/*valueTableColumn.setCellFactory(param -> new TextFieldTableCell<>());
		valueTableColumn.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition
				().getRow()).setValue(event.getNewValue()));*/

		valueTableColumn.setCellFactory(param -> new TableCell<CharacteristicValueViewModel, String>());

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
	}

	public void initialize() {
//		codeTextField.textProperty().bindBidirectional(viewModel.codeProperty());
		nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());

		initializeValuesTableView();
	}


}
