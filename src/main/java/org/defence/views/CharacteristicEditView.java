package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.defence.domain.entities.Characteristic;
import org.defence.infrastructure.DbHelper;
import org.defence.viewmodels.CharacteristicEditViewModel;
import org.defence.viewmodels.MeasurementTypeViewModel;

/**
 * Created by root on 8/12/15.
 */
public class CharacteristicEditView implements FxmlView<CharacteristicEditViewModel> {
    @FXML
    private Button okBtn;

    @FXML
    private TextField codeTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TableColumn<MeasurementTypeViewModel, Integer> idTableColumn;

    @FXML
    private TableColumn<MeasurementTypeViewModel, String> codeTableColumn;

    @FXML
    private TableColumn<MeasurementTypeViewModel, String> nameTableColumn;

    @FXML
    private TableColumn<MeasurementTypeViewModel, Boolean> checkBoxTableColumn;

    @FXML
    private TableView<MeasurementTypeViewModel> measurementsTableView;

    @InjectViewModel
    CharacteristicEditViewModel viewModel;

    private DbHelper dbHelper = DbHelper.getInstance();

    private void setOkBtnRegisterEvents() {
        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Characteristic characteristic = new Characteristic(codeTextField.getText(), nameTextField.getText());
                dbHelper.exportCharacteristic(characteristic);
            }
        });
    }

    private void initializeTableView() {
        measurementsTableView.setEditable(true);

        measurementsTableView.itemsProperty().bindBidirectional(viewModel.typesProperty());
        idTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        codeTableColumn.setCellValueFactory(new PropertyValueFactory("code"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        checkBoxTableColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        checkBoxTableColumn.setCellValueFactory(new PropertyValueFactory<>("isBelong"));

//        measurementsTableView.addLastColumn();

        /*measurementsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.selectedTypeProperty().unbind();
            viewModel.selectedTypeProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
        });*/
    }

    public void initialize() {
        initializeTableView();

        setOkBtnRegisterEvents();
    }
}
