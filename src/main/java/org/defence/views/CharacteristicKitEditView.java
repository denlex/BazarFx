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
import org.defence.viewmodels.CharacteristicKitEditViewModel;
import org.defence.viewmodels.CharacteristicViewModel;

/**
 * Created by root on 9/10/15.
 */
public class CharacteristicKitEditView implements FxmlView<CharacteristicKitEditViewModel>, Returnable {
    @FXML
    TextField nameTextField;

    @FXML
    TableView<CharacteristicViewModel> characteristicsTableView;

    @FXML
    TableColumn<CharacteristicViewModel, Integer> idTableColumn;

    @FXML
    TableColumn<CharacteristicViewModel, String> nameTableColumn;

    @FXML
    private TableColumn<CharacteristicViewModel, Boolean> checkBoxTableColumn;

    @InjectViewModel
    CharacteristicKitEditViewModel viewModel;

    private Stage stage;
    private DialogResult dialogResult = DialogResult.CANCEL;

    private void initializeTableView() {
        characteristicsTableView.setEditable(true);

        characteristicsTableView.itemsProperty().bindBidirectional(viewModel.allCharacteristicsProperty());
        idTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
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

    public DialogResult getDialogResult() {
        return dialogResult;
    }

    public void saveCharacteristicKitClicked() {
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

    @Override
    public DialogResult getModalResult() {
        return null;
    }

    public void initialize() {
        nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());

        initializeTableView();
    }
}
