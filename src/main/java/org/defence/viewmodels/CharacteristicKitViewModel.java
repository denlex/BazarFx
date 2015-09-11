package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import org.defence.domain.entities.CharacteristicKit;

/**
 * Created by root on 9/10/15.
 */
public class CharacteristicKitViewModel implements ViewModel {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final BooleanProperty isBelong = new SimpleBooleanProperty();
    private final ListProperty<CharacteristicViewModel> characteristics = new SimpleListProperty<>();

    public CharacteristicKitViewModel() {

    }

    public CharacteristicKitViewModel(CharacteristicKit characteristicKit) {
        this.setId(characteristicKit.getId());
        this.setName(characteristicKit.getName());
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

    public ObservableList<CharacteristicViewModel> getCharacteristics() {
        return characteristics.get();
    }

    public ListProperty<CharacteristicViewModel> characteristicsProperty() {
        return characteristics;
    }

    public void setCharacteristics(ObservableList<CharacteristicViewModel> characteristics) {
        this.characteristics.set(characteristics);
    }

    public boolean getIsBelong() {
        return isBelong.get();
    }

    public BooleanProperty isBelongProperty() {
        return isBelong;
    }

    public void setIsBelong(boolean isBelong) {
        this.isBelong.set(isBelong);
    }
}
