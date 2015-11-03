package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.defence.viewmodels.CharacteristicKitViewModel;
import org.defence.viewmodels.CharacteristicViewModel;
import org.defence.viewmodels.DescriptionFormatEditViewModel;

/**
 * Created by root on 9/11/15.
 */
public class DescriptionFormatEditView implements FxmlView<DescriptionFormatEditViewModel>, Returnable {

    @FXML
    TextField codeTextField;

    @FXML
    TextField nameTextField;

    @FXML
    TableView<CharacteristicViewModel> characteristicsTableView;

    @FXML
    TableColumn<CharacteristicKitViewModel, Integer> idTableColumn;

    @FXML
    TableColumn<CharacteristicKitViewModel, Integer> codeTableColumn;

    @FXML
    TableColumn<CharacteristicKitViewModel, Integer> nameTableColumn;

    @FXML
    TableColumn<CharacteristicKitViewModel, Boolean> checkBoxTableColumn;

    @InjectViewModel
    DescriptionFormatEditViewModel viewModel;

    private Stage stage;
    private DialogResult dialogResult = DialogResult.CANCEL;

    private void initializeTableView() {
        characteristicsTableView.setEditable(true);

        characteristicsTableView.itemsProperty().bindBidirectional(viewModel.allCharacteristicsProperty());
        idTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        codeTableColumn.setCellValueFactory(new PropertyValueFactory("code"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        checkBoxTableColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        checkBoxTableColumn.setCellValueFactory(new PropertyValueFactory<>("isBelong"));

        characteristicsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.selectedCharacteristicProperty().unbind();
            viewModel.selectedCharacteristicProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
        });
    }

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
		stage.onShownProperty().bindBidirectional(viewModel.shownWindowProperty());
	}

	public void initialize() {
		codeTextField.textProperty().bindBidirectional(viewModel.codeProperty());
		nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());

		initializeTableView();
	}
}
