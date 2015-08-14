package org.defence.controllers;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;
import org.defence.domain.entities.MeasurementType;
import org.defence.infrastructure.DbHelper;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by root on 8/12/15.
 */
public class NewMeasurementController implements Initializable {
    @FXML
    private ComboBox<MeasurementType> measurementTypeComboBox;

    private DbHelper dbHelper = new DbHelper();

    private void measurementTypeComboBoxRegisterEvents() {
        measurementTypeComboBox.setOnAction(event -> {
//                JOptionPane.showMessageDialog(null, measurementTypeComboBox.getValue().getName());
        });

        // Define rendering of the list of values in ComboBox drop down.
        measurementTypeComboBox.setCellFactory((comboBox) -> new ListCell<MeasurementType>() {
            @Override
            protected void updateItem(MeasurementType item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        // Define rendering of selected value shown in ComboBox.
        measurementTypeComboBox.setConverter(new StringConverter<MeasurementType>() {
            @Override
            public String toString(MeasurementType measurementType) {
                if (measurementType == null) {
                    return null;
                } else {
                    return measurementType.getName();
                }
            }

            @Override
            public MeasurementType fromString(String measurementTypeString) {
                return null; // No conversion fromString needed.
            }
        });
    }

    private ObservableList<MeasurementType> getAllMeasurementTypes() {
        return new ObservableListWrapper<>(dbHelper.importAllMeasurementTypes());
    }

    private int importMeasurementTypesIntoComboBox() {
        for (MeasurementType measurementType : getAllMeasurementTypes()) {

            measurementTypeComboBox.getItems().add(measurementType);
        }

//        measurementTypeComboBox.getItems().

        return measurementTypeComboBox.getItems().size();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        importMeasurementTypesIntoComboBox();
        measurementTypeComboBoxRegisterEvents();
    }
}
