package org.defence.viewmodels;

import com.sun.javafx.collections.ObservableSetWrapper;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import org.defence.domain.entities.CharacteristicType;

/**
 * Created by root on 06.09.15.
 */
public class CharacteristicTypeViewModel implements ViewModel {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty code = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final SetProperty<CharacteristicViewModel> characteristics = new SimpleSetProperty<>();

    public CharacteristicTypeViewModel(CharacteristicType type) {
        id.setValue(type.getId());
        code.setValue(type.getCode());
        name.setValue(type.getName());
//        characteristics.setValue(new ObservableSetWrapper<>(type.getCharacteristics()));
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

    public ObservableList<CharacteristicViewModel> getCharacteristics() {
        return characteristics.get();
    }

    public ListProperty<CharacteristicViewModel> characteristicsProperty() {
        return characteristics;
    }

    public void setCharacteristics(ObservableList<CharacteristicViewModel> characteristics) {
        this.characteristics.set(characteristics);
    }
}
