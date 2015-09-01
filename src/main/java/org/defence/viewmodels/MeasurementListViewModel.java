package org.defence.viewmodels;

import com.sun.javafx.collections.ObservableListWrapper;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import org.defence.domain.entities.Measurement;
import org.defence.domain.entities.MeasurementType;
import org.defence.infrastructure.DbHelper;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/1/15.
 */
public class MeasurementListViewModel implements ViewModel {
    private final ListProperty<MeasurementTypeViewModel> types = new SimpleListProperty<>();
    private final ListProperty<MeasurementViewModel> measurements = new SimpleListProperty<>();

    private final ObjectProperty<MeasurementTypeViewModel> selectedType = new SimpleObjectProperty<>();
    private final ObjectProperty<MeasurementTypeViewModel> selectedMeasurement = new SimpleObjectProperty<>();

    private DbHelper dbHelper = DbHelper.getInstance();

    private Command testCommand;

    public MeasurementListViewModel() {
        List<MeasurementTypeViewModel> typeList = new ArrayList<>();
        for (MeasurementType type : dbHelper.importAllMeasurementTypes()) {
            typeList.add(new MeasurementTypeViewModel(type));
        }

        types.set(new ObservableListWrapper<>(typeList));

        List<MeasurementViewModel> measurementList = new ArrayList<>();
        for (Measurement measurement : dbHelper.importAllMeasurements()) {
            measurementList.add(new MeasurementViewModel(measurement));
        }

        measurements.set(new ObservableListWrapper<>(measurementList));

        testCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                JOptionPane.showMessageDialog(null, selectedType.get().codeProperty() + "\n" + selectedType.get().nameProperty());
            }
        });
    }

    private ObservableList<MeasurementType> getAllMeasurementTypes() {
        return new ObservableListWrapper<>(dbHelper.importAllMeasurementTypes());
    }

    private int importMeasurementTypesIntoComboBox() {
        for (MeasurementType measurementType : getAllMeasurementTypes()) {

//            measurementTypeComboBox.getItems().add(measurementType);
        }

//        measurementTypeComboBox.getItems().

//        return measurementTypeComboBox.getItems().size();
        return 0;
    }

    public ObservableList<MeasurementTypeViewModel> getTypes() {
        return types.get();
    }

    public ListProperty<MeasurementTypeViewModel> typesProperty() {
        return types;
    }

    public void setTypes(ObservableList<MeasurementTypeViewModel> types) {
        this.types.set(types);
    }

    public ObservableList<MeasurementViewModel> getMeasurements() {
        return measurements.get();
    }

    public ListProperty<MeasurementViewModel> measurementsProperty() {
        return measurements;
    }

    public void setMeasurements(ObservableList<MeasurementViewModel> measurements) {
        this.measurements.set(measurements);
    }

    public MeasurementTypeViewModel getSelectedType() {
        return selectedType.get();
    }

    public ObjectProperty<MeasurementTypeViewModel> selectedTypeProperty() {
        return selectedType;
    }

    public void setSelectedType(MeasurementTypeViewModel selectedType) {
        this.selectedType.set(selectedType);
    }

    public MeasurementTypeViewModel getSelectedMeasurement() {
        return selectedMeasurement.get();
    }

    public ObjectProperty<MeasurementTypeViewModel> selectedMeasurementProperty() {
        return selectedMeasurement;
    }

    public void setSelectedMeasurement(MeasurementTypeViewModel selectedMeasurement) {
        this.selectedMeasurement.set(selectedMeasurement);
    }

    public Command getTestCommand() {
        return testCommand;
    }
}