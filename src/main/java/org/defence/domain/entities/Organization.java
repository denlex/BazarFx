package org.defence.domain.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by root on 22.07.15.
 */
public class Organization {
    private IntegerProperty id;
    private StringProperty code;
    private StringProperty name;

    public Organization() {
        id = new SimpleIntegerProperty();
    }

    public Organization(String code, String name) {
        this();
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
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
}
