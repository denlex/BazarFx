package org.defence.domain.entities;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by root on 22.07.15.
 */
public class CharacteristicType implements Serializable {
    private IntegerProperty id;
    private StringProperty code;
    private StringProperty name;
    private SetProperty<Characteristic> characteristics;// = new SimpleSetProperty<>(FXCollections.observableSet());

    protected CharacteristicType() {
        id = new SimpleIntegerProperty();
    }

    protected CharacteristicType(String code, String name) {
        this();
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
    }

    public CharacteristicType(String code, String name, Set<Characteristic> characteristics) {
        this(code, name);
        this.characteristics = new SimpleSetProperty<>(FXCollections.observableSet(characteristics));
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

    public ObservableSet<Characteristic> getCharacteristics() {
        return characteristics.get();
    }

    public SetProperty<Characteristic> characteristicsProperty() {
        return characteristics;
    }

    public void setCharacteristics(ObservableSet<Characteristic> characteristics) {
        this.characteristics.set(characteristics);
    }
}
