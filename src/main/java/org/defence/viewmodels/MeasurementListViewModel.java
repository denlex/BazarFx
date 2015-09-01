package org.defence.viewmodels;

import com.sun.javafx.collections.ObservableListWrapper;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import org.defence.domain.entities.Measurement;
import org.defence.domain.entities.MeasurementType;
import org.defence.infrastructure.DbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/1/15.
 */
public class MeasurementListViewModel implements ViewModel {
    private final ListProperty<MeasurementTypeViewModel> types = new SimpleListProperty<>();
    private final ListProperty<MeasurementViewModel> measurements = new SimpleListProperty<>();

    private DbHelper dbHelper = DbHelper.getInstance();

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
}
