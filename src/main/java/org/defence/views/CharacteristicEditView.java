package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.defence.viewmodels.CharacteristicEditViewModel;
import org.defence.viewmodels.MeasurementViewModel;

/**
 * Created by root on 8/12/15.
 */
public class CharacteristicEditView implements FxmlView<CharacteristicEditViewModel>, Returnable {
    @FXML
    private Button okBtn;

    @FXML
    private TextField codeTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TableColumn<MeasurementViewModel, Integer> idTableColumn;

    @FXML
    private TableColumn<MeasurementViewModel, String> codeTableColumn;

    @FXML
    private TableColumn<MeasurementViewModel, String> nameTableColumn;

    @FXML
    private TableColumn<MeasurementViewModel, Boolean> checkBoxTableColumn;

    @FXML
    private TableView<MeasurementViewModel> measurementsTableView;

    @InjectViewModel
    CharacteristicEditViewModel viewModel;

    private Stage stage;
    private DialogResult dialogResult = DialogResult.CANCEL;

    private void initializeTableView() {
        measurementsTableView.setEditable(true);

        measurementsTableView.itemsProperty().bindBidirectional(viewModel.measurementsProperty());
        idTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        codeTableColumn.setCellValueFactory(new PropertyValueFactory("code"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        checkBoxTableColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        checkBoxTableColumn.setCellValueFactory(new PropertyValueFactory<>("isBelong"));


//        checkBoxTableColumn.setCellValueFactory(new PropertyValueFactory<>("isBelong"));

//        measurementsTableView.addLastColumn();

        measurementsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.selectedMeasurementProperty().unbind();
            viewModel.selectedMeasurementProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
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
        viewModel.getCancelCommand().execute();
        dialogResult = DialogResult.CANCEL;
        stage.close();
    }

    public void initialize() {
        codeTextField.textProperty().bindBidirectional(viewModel.codeProperty());
        nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());

        initializeTableView();
    }
}
