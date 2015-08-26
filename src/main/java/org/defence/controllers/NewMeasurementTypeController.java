package org.defence.controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.defence.viewmodel.MeasurementTypeViewModel;
import org.defence.viewmodel.MeasurementTypesViewModel;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by root on 8/12/15.
 */
public class NewMeasurementTypeController implements Initializable {
    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField codeTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    TableView<MeasurementTypeViewModel> typesTableView;

    @FXML
    TableColumn<MeasurementTypeViewModel, IntegerProperty> idTableColumn;

    @FXML
    TableColumn<MeasurementTypeViewModel, StringProperty> codeTableColumn;

    @FXML
    TableColumn<MeasurementTypeViewModel, StringProperty> nameTableColumn;

    @FXML
    private Label messageLabel;

    private MeasurementTypesViewModel measurementTypesViewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change change) -> {
            System.out.println(change.getControlNewText());

            if (typeViewModel.validAgeInput(change.getControlNewText())) {
                // accept
                return change ;
            } else {
                // reject
                return null ;
            }
        };

        TextFormatter<Integer> ageFormatter = new TextFormatter<>(new IntegerStringConverter(), null, filter);
        age.setTextFormatter(ageFormatter);
        ageFormatter.valueProperty().bindBidirectional(typeViewModel.ageProperty().asObject());*/

        MeasurementTypeViewModel typeViewModel = new MeasurementTypeViewModel();
        codeTextField.textProperty().bindBidirectional(typeViewModel.codeProperty());
        nameTextField.textProperty().bindBidirectional(typeViewModel.nameProperty());

        measurementTypesViewModel = new MeasurementTypesViewModel(typeViewModel);

        // make addBtn disable if codeTextField and nameTextField are empty
        addBtn.disableProperty().bind(typeViewModel.isActionPossibleProperty());
        addBtn.onActionProperty().bindBidirectional(measurementTypesViewModel.addBtnProperty());

        // binding ObservableCollection "Types" on the TableView
        typesTableView.itemsProperty().bindBidirectional(new SimpleObjectProperty<>(measurementTypesViewModel.getTypes()));

        deleteBtn.onActionProperty().bindBidirectional(measurementTypesViewModel.deleteBtnProperty());
//        measurementTypesViewModel.selectedRowProperty().bindBidirectional(typesTableView.selectionModelProperty().get().selectedItemProperty());

//        typesTableView.selectionModelProperty().bindBidirectional(measurementTypesViewModel.selectedRowProperty());

        initializeTableView();

        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JOptionPane.showMessageDialog(null, typesTableView.getItems().get(0).getName());
            }
        });
    }

    private void initializeTableView() {
        idTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        codeTableColumn.setCellValueFactory(new PropertyValueFactory("code"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));

        typesTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            measurementTypesViewModel.selectedRowProperty().unbind();
            measurementTypesViewModel.selectedRowProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
        });

    }
}
