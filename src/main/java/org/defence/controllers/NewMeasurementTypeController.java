package org.defence.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.defence.domain.entities.MeasurementType;
import org.defence.infrastructure.DbHelper;
import org.defence.viewmodel.MeasurementTypeViewModel;
import org.defence.viewmodel.TypesViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by root on 8/12/15.
 */
public class NewMeasurementTypeController implements Initializable {
    @FXML
    private Button okBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField codeTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    TableView<MeasurementTypeViewModel> typesTableView;

    @FXML
    private Label messageLabel;

    private DbHelper dbHelper = new DbHelper();

    //    private MeasurementTypeViewModel viewModel;
    private TypesViewModel typesViewModel;

    private void setOkBtnRegisterEvents() {
        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*JOptionPane.showMessageDialog(null, "Code:\t" + codeTextField.getText()
                    + "\nName: \t" + nameTextField.getText());*/
                MeasurementType measurementType = new MeasurementType(codeTextField.getText(), nameTextField.getText());
                /*if ( dbHelper.exportMeasurementType(measurementType) ) {
                    messageLabel.setText("Тип измерения успешно добавлен");

                    DropShadow effect = new DropShadow();
                    effect.setRadius(0);
                    effect.setOffsetY(1);
                    effect.setColor(Color.WHITE);
                    messageLabel.setEffect(effect);
                }*/

                /*if (viewModel != null) {
                    viewModel.getTypes().add(measurementType);
                }

                showCollection(viewModel.getTypes());*/
            }
        });
    }

    private void setCancelBtnRegisterEvents() {
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*JOptionPane.showMessageDialog(null, "Code:\t" + codeTextField.getText()
                    + "\nName: \t" + nameTextField.getText());*/
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        viewModel = new MeasurementTypeViewModel();

        /*UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change change) -> {
            System.out.println(change.getControlNewText());

            if (viewModel.validAgeInput(change.getControlNewText())) {
                // accept
                return change ;
            } else {
                // reject
                return null ;
            }
        };

        TextFormatter<Integer> ageFormatter = new TextFormatter<>(new IntegerStringConverter(), null, filter);
        age.setTextFormatter(ageFormatter);
        ageFormatter.valueProperty().bindBidirectional(viewModel.ageProperty().asObject());*/

        MeasurementTypeViewModel viewModel = new MeasurementTypeViewModel();
        codeTextField.textProperty().bindBidirectional(viewModel.codeProperty());
        nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());

        typesViewModel = new TypesViewModel(viewModel);

        okBtn.disableProperty().bind(viewModel.isActionPossibleProperty());

        okBtn.onActionProperty().bindBidirectional(typesViewModel.btnProperty());

        ObjectProperty<ObservableList<MeasurementTypeViewModel>> typesProp = new SimpleObjectProperty<>(typesViewModel.getTypes());
        typesTableView.itemsProperty().bindBidirectional(typesProp);

//        setOkBtnRegisterEvents();

        initializeTableView();
        setCancelBtnRegisterEvents();
    }

    private void initializeTableView() {
        // Define rendering of the list of values in ComboBox drop down.

        TableColumn<MeasurementTypeViewModel, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory("code"));
        TableColumn<MeasurementTypeViewModel, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));

        typesTableView.getColumns().addAll(codeCol, nameCol);
    }
}
