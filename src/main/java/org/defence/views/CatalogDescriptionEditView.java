package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.defence.viewmodels.CatalogDescriptionEditViewModel;
import org.defence.viewmodels.CharacteristicValueViewModel;
import org.defence.viewmodels.CharacteristicViewModel;
import org.defence.viewmodels.MeasurementViewModel;

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
	TableColumn<CharacteristicViewModel, String> characteristicTableColumn;

	@FXML
	TableColumn<CharacteristicValueViewModel, String> valueTableColumn;

	@FXML
	TableColumn<MeasurementViewModel, String> measurementTableColumn;

	@InjectViewModel
	CatalogDescriptionEditViewModel viewModel;

	Stage stage;
	DialogResult dialogResult = DialogResult.CANCEL;

	private void initializeValuesTableView() {
		valuesTableView.itemsProperty().bindBidirectional(viewModel.valuesProperty());
		characteristicTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
		valueTableColumn.setCellValueFactory(new PropertyValueFactory("value"));
		measurementTableColumn.setCellValueFactory(new PropertyValueFactory("shortName"));
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

	public void initialize() {
//		codeTextField.textProperty().bindBidirectional(viewModel.codeProperty());
		nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());
	}
}
