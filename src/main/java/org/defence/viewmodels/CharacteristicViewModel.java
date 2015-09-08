package org.defence.viewmodels;

import com.sun.javafx.collections.ObservableSetWrapper;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.ObservableSet;
import org.defence.domain.entities.Characteristic;
import org.defence.domain.entities.Measurement;

/**
 * Created by root on 06.09.15.
 */
public class CharacteristicViewModel implements ViewModel {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty code = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final SetProperty<Measurement> measurements = new SimpleSetProperty<>();
    private final StringProperty measurementText = new SimpleStringProperty();

    public CharacteristicViewModel() {
    }

    public CharacteristicViewModel(Characteristic characteristic) {
        id.setValue(characteristic.getId());
        code.setValue(characteristic.getCode());
        name.setValue(characteristic.getName());
        measurements.setValue(new ObservableSetWrapper<>(characteristic.getMeasurements()));

        StringBuilder text = new StringBuilder();
        for (Measurement elem : measurements.getValue()) {
            text.append(elem.getName());
            text.append(";");
        }

        // if last index of text is ';', then delete this character
        if (text.length() > 1 && text.charAt(text.length() - 1) == ';') {
            text.deleteCharAt(text.length() - 1);
        }

        measurementText.setValue(text.toString());
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getCode() {
        return code.get();
    }

    public StringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableSet<Measurement> getMeasurements() {
        return measurements.get();
    }

    public SetProperty<Measurement> measurementsProperty() {
        return measurements;
    }

    public void setMeasurements(ObservableSet<Measurement> measurements) {
        this.measurements.set(measurements);
    }

    public String getMeasurementText() {
        return measurementText.get();
    }

    public StringProperty measurementTextProperty() {
        return measurementText;
    }

    public void setMeasurementText(String measurementText) {
        this.measurementText.set(measurementText);
    }
}
