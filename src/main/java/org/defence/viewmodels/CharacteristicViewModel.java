package org.defence.viewmodels;

import com.sun.javafx.collections.ObservableSetWrapper;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.ObservableSet;
import org.defence.domain.entities.Characteristic;

/**
 * Created by root on 06.09.15.
 */
public class CharacteristicViewModel implements ViewModel {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty code = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final SetProperty<Characteristic> measurements = new SimpleSetProperty<>();

    public CharacteristicViewModel() {
    }

    public CharacteristicViewModel(Characteristic characteristic) {
        id.setValue(characteristic.getId());
        code.setValue(characteristic.getCode());
        name.setValue(characteristic.getName());
        measurements.setValue(new ObservableSetWrapper<>(characteristic.getCharacteristics()));
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

    public ObservableSet<Characteristic> getMeasurements() {
        return measurements.get();
    }

    public SetProperty<Characteristic> measurementsProperty() {
        return measurements;
    }

    public void setMeasurements(ObservableSet<Characteristic> measurements) {
        this.measurements.set(measurements);
    }
}
