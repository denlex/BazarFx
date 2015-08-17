package org.defence.domain.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * Created by root on 08.08.15.
 */
public class CharacteristicValue implements Serializable {
    private IntegerProperty id;
    private StringProperty value;
    private Characteristic characteristic;

    protected CharacteristicValue() {
        id = new SimpleIntegerProperty();
    }

    public CharacteristicValue(Characteristic characteristic, String value) {
        this.value = new SimpleStringProperty(value);
        this.characteristic = characteristic;
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

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }
}
