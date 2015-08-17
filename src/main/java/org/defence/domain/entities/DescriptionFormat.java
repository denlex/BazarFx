package org.defence.domain.entities;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.util.Set;

/**
 * Created by root on 22.07.15.
 */
public class DescriptionFormat {
    private IntegerProperty id;
    private StringProperty code;
    private StringProperty name;
    private SetProperty<CharacteristicKit> characteristicKits;
    private SetProperty<CatalogDescription> catalogDescriptions;

    public DescriptionFormat() {
        id = new SimpleIntegerProperty();
    }

    protected DescriptionFormat(String code, String name) {
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
    }

    public DescriptionFormat(String code, String name, Set<CharacteristicKit> characteristicKits, Set<CatalogDescription> catalogDescriptions) {
        this(code, name);
        this.characteristicKits = new SimpleSetProperty<>(FXCollections.observableSet(characteristicKits));
        this.catalogDescriptions = new SimpleSetProperty<>(FXCollections.observableSet(catalogDescriptions));
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

    public ObservableSet<CharacteristicKit> getCharacteristicKits() {
        return characteristicKits.get();
    }

    public SetProperty<CharacteristicKit> characteristicKitsProperty() {
        return characteristicKits;
    }

    public void setCharacteristicKits(ObservableSet<CharacteristicKit> characteristicKits) {
        this.characteristicKits.set(characteristicKits);
    }

    public ObservableSet<CatalogDescription> getCatalogDescriptions() {
        return catalogDescriptions.get();
    }

    public SetProperty<CatalogDescription> catalogDescriptionsProperty() {
        return catalogDescriptions;
    }

    public void setCatalogDescriptions(ObservableSet<CatalogDescription> catalogDescriptions) {
        this.catalogDescriptions.set(catalogDescriptions);
    }
}
