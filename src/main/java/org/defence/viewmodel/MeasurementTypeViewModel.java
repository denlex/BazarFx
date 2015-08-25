package org.defence.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import org.defence.domain.entities.MeasurementType;

/**
 * Created by root on 8/19/15.
 */
public class MeasurementTypeViewModel extends AbstractViewModel<MeasurementType> {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty code = new SimpleStringProperty();
    private final ReadOnlyBooleanWrapper actionPossible = new ReadOnlyBooleanWrapper();

    private final IntegerProperty age = new SimpleIntegerProperty();
    private final ListProperty<MeasurementType> types = new SimpleListProperty<>();

    BooleanBinding nameInvalid = new BooleanBinding() {
        @Override
        protected boolean computeValue() {
            return false;
        }
    };

    public MeasurementTypeViewModel() {
        actionPossible.bind(name.isEmpty().or(code.isEmpty()));
    }

    public MeasurementTypeViewModel(MeasurementTypeViewModel viewModel) {
        code.set(viewModel.getCode());
        name.set(viewModel.getName());
    }

    public MeasurementTypeViewModel(String code, String name) {
        this.code.set(code);
        this.name.set(name);
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
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

    public ReadOnlyBooleanProperty isActionPossibleProperty() {
        return actionPossible.getReadOnlyProperty();
    }

    public boolean validAgeInput(String input) {
        // must support partial entry while editing, including empty string
        // accept any integer from 0 - 135 (arbitrary upper bound example)
        String regex = "([0-9]{0,2})|(1[0-2][0-9])|(13[0-5])";
        return input.matches(regex);
    }

    public Integer getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public javafx.collections.ObservableList<MeasurementType> getTypes() {
        return types.get();
    }

    public ListProperty<MeasurementType> typesProperty() {
        return types;
    }

    /*@Override
    public boolean equals(Object obj) {

        if (obj == null && !(obj instanceof MeasurementTypeViewModel)) {
            return false;
        }

        return getId() == ((MeasurementTypeViewModel) obj).getId();

//        return getCode().equals(((MeasurementTypeViewModel) obj).getCode())
//                && getName().equals(((MeasurementTypeViewModel) obj).getName()) ? true : false;
    }*/
}
