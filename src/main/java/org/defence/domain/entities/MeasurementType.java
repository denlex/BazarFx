package org.defence.domain.entities;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by root on 22.07.15.
 */
public class MeasurementType implements Serializable {
    private IntegerProperty id = new SimpleIntegerProperty();;
    private StringProperty code;
    private StringProperty name;
    private SetProperty<Measurement> measurements = new SimpleSetProperty<>();

    public MeasurementType() {
    }

    public MeasurementType(String code, String name) {
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
    }

    public MeasurementType(String code, String name, Set<Measurement> measurements) {
        this(code, name);
        this.measurements.set(FXCollections.observableSet(measurements));
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
}
