package org.defence.viewmodels;

import com.sun.javafx.collections.ObservableListWrapper;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import org.defence.domain.entities.Measurement;
import org.defence.domain.entities.MeasurementType;
import org.defence.infrastructure.DbHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 9/2/15.
 */
public class MeasurementCatalogViewModel implements ViewModel {
    private final ListProperty<MeasurementTypeViewModel> types = new SimpleListProperty<>();
    private final ListProperty<MeasurementViewModel> measurements = new SimpleListProperty<>();

    private final ObjectProperty<MeasurementTypeViewModel> selectedType = new SimpleObjectProperty<>();
    private final ObjectProperty<MeasurementViewModel> selectedMeasurement = new SimpleObjectProperty<>();

    private final DbHelper dbHelper = DbHelper.getInstance();

    public MeasurementCatalogViewModel() {
        loadAllTypes();
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

    public MeasurementViewModel getSelectedMeasurement() {
        return selectedMeasurement.get();
    }

    public ObjectProperty<MeasurementViewModel> selectedMeasurementProperty() {
        return selectedMeasurement;
    }

    public void setSelectedMeasurement(MeasurementViewModel selectedMeasurement) {
        this.selectedMeasurement.set(selectedMeasurement);
    }

    public void loadAllTypes() {
        List<MeasurementTypeViewModel> typeList = new LinkedList<>();

        for (MeasurementType type : dbHelper.getAllMeasurementTypes()) {
            typeList.add(new MeasurementTypeViewModel(type));
        }

        types.set(new ObservableListWrapper<>(typeList));
    }

    public void loadMeasurementsBySelectedType() {
        // if user selected any type in typeTableView
        if (selectedTypeProperty().get() != null) {
            List<MeasurementViewModel> measurementList = new LinkedList<>();

            for (Measurement measurement : dbHelper.getMeasurementsByMeasurementTypeId(selectedType.getValue().getId())) {
                measurementList.add(new MeasurementViewModel(measurement));
            }

            measurements.set(new ObservableListWrapper<>(measurementList));
        }
    }
}
