package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import org.defence.domain.entities.Measurement;

/**
 * Created by root on 30.08.15.
 */
public class MeasurementViewModel implements ViewModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty code = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty shortName = new SimpleStringProperty();
    private final BooleanProperty isBelong = new SimpleBooleanProperty();


    public MeasurementViewModel() {
    }

    public MeasurementViewModel(Measurement measurement) {
        id.setValue(measurement.getId());
        code.setValue(measurement.getCode());
        name.setValue(measurement.getName());
        shortName.setValue(measurement.getShortName());
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

    public String getShortName() {
        return shortName.get();
    }

    public StringProperty shortNameProperty() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName.set(shortName);
    }
}
