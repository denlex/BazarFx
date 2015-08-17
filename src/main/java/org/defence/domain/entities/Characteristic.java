package org.defence.domain.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * Created by root on 22.07.15.
 */
public class Characteristic implements Serializable {
    private IntegerProperty id;
    private StringProperty code;
    private StringProperty name;
    private Measurement measurement;

    protected Characteristic() {
        id = new SimpleIntegerProperty();
    }

    protected Characteristic(String code, String name) {
        this();
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
    }

    public Characteristic(String code, String name, Measurement measurement) {
        this(code, name);
        this.measurement = measurement;
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

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }
}
