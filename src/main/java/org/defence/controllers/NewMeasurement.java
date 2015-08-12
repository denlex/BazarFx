package org.defence.controllers;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import org.defence.domain.entities.MeasurementType;
import org.defence.infrastructure.DbHelper;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by root on 8/12/15.
 */
public class NewMeasurement implements Initializable {
    @FXML
    private ComboBox<MeasurementType> measurementTypeComboBox;

    private DbHelper dbHelper = new DbHelper();

    private ObservableList<MeasurementType> getAllMeasurementTypes() {
        return new ObservableListWrapper<>(dbHelper.importAllMeasurementTypes());
    }

    private int importMeasurementTypesIntoComboBox() {
        measurementTypeComboBox.setItems(getAllMeasurementTypes());

        return measurementTypeComboBox.getItems().size();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        importMeasurementTypesIntoComboBox();
    }
}
