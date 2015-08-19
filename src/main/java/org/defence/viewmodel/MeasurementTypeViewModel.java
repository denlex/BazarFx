package org.defence.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.ObservableSet;
import org.defence.domain.entities.MeasurementType;

/**
 * Created by root on 8/19/15.
 */
public class MeasurementTypeViewModel extends AbstractViewModel<MeasurementType> {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty code = new SimpleStringProperty();
    private final ReadOnlyBooleanWrapper actionPossible = new ReadOnlyBooleanWrapper();
    BooleanBinding nameInvalid = new BooleanBinding() {
        @Override
        protected boolean computeValue() {
            return false;
        }
    };

    private final SetProperty<MeasurementType> types = new SimpleSetProperty<>();

//    private final Property<SetProperty<MeasurementType>>

    public MeasurementTypeViewModel() {
        actionPossible.bind(name.isEmpty().or(code.isEmpty()));
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

    public String getCode() {
        return code.get();
    }

    public StringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public ReadOnlyBooleanWrapper actionPossibleProperty() {
        return actionPossible;
    }

    public ObservableSet<MeasurementType> getTypes() {
        return types.get();
    }

    public SetProperty<MeasurementType> typesProperty() {
        return types;
    }

    public ReadOnlyBooleanProperty isActionPossibleProperty() {
        return actionPossible.getReadOnlyProperty();
    }
}
