package org.defence.domain.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by root on 22.07.15.
 */
public class MeasurementType implements Serializable {
    private IntegerProperty id;
    private StringProperty code;
    private StringProperty name;
    private ObservableSet<Measurement> measurements;

    public MeasurementType() {
    }

    public MeasurementType(String code, String name) {
        this(code, name, null);
    }

    public MeasurementType(String code, String name, Set<Measurement> measurements) {
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
        this.measurements = FXCollections.emptyObservableSet();
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getCode() {
        return code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public StringProperty codeProperty() {
        return code;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObservableSet<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(ObservableSet<Measurement> measurements) {
        this.measurements.addAll(measurements);
    }

}
