package org.defence.domain.entities;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by root on 8/6/15.
 */
public class CharacteristicKit implements Serializable {
    private IntegerProperty id;
    private StringProperty name;
    private SetProperty<Characteristic> characteristics;// = new SimpleSetProperty<>(FXCollections.observableSet());

    protected CharacteristicKit() {
        id = new SimpleIntegerProperty();
    }

    protected CharacteristicKit(String name) {
        this();
        this.name = new SimpleStringProperty(name);
    }

    public CharacteristicKit(String name, Set<Characteristic> characteristics) {
        this(name);
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
