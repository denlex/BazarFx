package org.defence.domain.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * Created by root on 22.07.15.
 */
public class Measurement implements Serializable {
    private IntegerProperty id;
    private StringProperty code;
    private StringProperty name;
    private StringProperty shortName;

    protected Measurement() {
        id = new SimpleIntegerProperty();
    }

    public Measurement(String code, String name, String shortName) {
        this();
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
        this.shortName = new SimpleStringProperty(shortName);
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

    public String getShortName() {
        return shortName.get();
    }

    public StringProperty shortNameProperty() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName.set(shortName);
    }
}
