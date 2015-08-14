package org.defence.tests.entities;

import javafx.beans.property.*;
import javafx.util.converter.DateStringConverter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by root on 8/14/15.
 */
public class Certificate implements Serializable {

    private IntegerProperty id;
    private StringProperty name;
    private ObjectProperty<LocalDate> receptionDate;

    public Certificate() {
    }

    public Certificate(String name, Date receptionDate) {
        this.name = new SimpleStringProperty(name);
        DateStringConverter dateStringConverter = new DateStringConverter();
        this.receptionDate = new SimpleObjectProperty<LocalDate>(LocalDate.parse(receptionDate.toString()));
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public LocalDate getReceptionDate() {
        return receptionDate.get();
    }

    public ObjectProperty<LocalDate> receptionDateProperty() {
        return receptionDate;
    }

    public void setReceptionDate(LocalDate receptionDate) {
        this.receptionDate.set(receptionDate);
    }
}
