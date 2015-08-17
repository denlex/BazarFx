package org.defence.tests.entities;

import javafx.beans.property.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by root on 8/14/15.
 */
public class Certificate implements Serializable {

    private IntegerProperty id;
    private StringProperty name;
    private ObjectProperty<Date> receptionDate;

    public Certificate() {
        id = new SimpleIntegerProperty();
    }

    public Certificate(String name, Date receptionDate) {
        this();
        this.name = new SimpleStringProperty(name);
        this.receptionDate = new SimpleObjectProperty<>(receptionDate);
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

    public Date getReceptionDate() {
        return receptionDate.get();
    }

    public ObjectProperty<Date> receptionDateProperty() {
        return receptionDate;
    }

    public void setReceptionDate(Date receptionDate) {
        this.receptionDate.set(receptionDate);
    }
}
