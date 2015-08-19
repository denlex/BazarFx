package org.defence.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import org.defence.domain.entities.MeasurementType;
import org.defence.infrastructure.DbHelper;
import org.defence.viewmodel.MeasurementTypeViewModel;

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
    TableView<MeasurementType> typesTableView;

    @FXML
    private Label messageLabel;

    private DbHelper dbHelper = new DbHelper();

    private MeasurementTypeViewModel viewModel;

    private void setOkBtnRegisterEvents() {
        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*JOptionPane.showMessageDialog(null, "Code:\t" + codeTextField.getText()
                    + "\nName: \t" + nameTextField.getText());*/
                MeasurementType measurementType = new MeasurementType(codeTextField.getText(), nameTextField.getText());
                if ( dbHelper.exportMeasurementType(measurementType) ) {
                    messageLabel.setText("Тип измерения успешно добавлен");

                    DropShadow effect = new DropShadow();
                    effect.setRadius(0);
                    effect.setOffsetY(1);
                    effect.setColor(Color.WHITE);
                    messageLabel.setEffect(effect);
                }
            }
        });
    }

    private void setCancelBtnRegisterEvents() {
        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*JOptionPane.showMessageDialog(null, "Code:\t" + codeTextField.getText()
                    + "\nName: \t" + nameTextField.getText());*/
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*ValidationSupport validationSupport = new ValidationSupport();
        Validator<String> validator = new Validator<String>() {
            @Override
            public ValidationResult apply(Control control, String s) {
                boolean condition = s != null ? !s.matches("[a-z]") : false;

                return ValidationResult.fromMessageIf(control, "not a number", Severity.ERROR, condition);
            }
        };

        validationSupport.registerValidator(nameTextField, true, validator);*/

        viewModel = new MeasurementTypeViewModel();

        nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());
        codeTextField.textProperty().bindBidirectional(viewModel.codeProperty());
        okBtn.disableProperty().bind(viewModel.isActionPossibleProperty());

//        typesTableView.itemsProperty().bindBidirectional();

        setOkBtnRegisterEvents();
        setCancelBtnRegisterEvents();
    }
}
