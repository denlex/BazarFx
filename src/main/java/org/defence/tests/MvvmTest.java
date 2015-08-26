package org.defence.tests;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by root on 8/26/15.
 */

class Type {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int id;
    private String name;
}

class TypeViewModel {
    private Type type;

    private final IntegerProperty idProperty = new SimpleIntegerProperty();
    private final StringProperty nameProperty = new SimpleStringProperty();

    public TypeViewModel(Type type) {
        this.type = type;

        idPropertyProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                TypeViewModel.this.type.setId(newValue.intValue());
            }
        });

        namePropertyProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                TypeViewModel.this.type.setName(newValue);
            }
        });
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getIdProperty() {
        return idProperty.get();
    }

    public IntegerProperty idPropertyProperty() {
        return idProperty;
    }

    public void setIdProperty(int idProperty) {
        this.idProperty.set(idProperty);
    }

    public String getNameProperty() {
        return nameProperty.get();
    }

    public StringProperty namePropertyProperty() {
        return nameProperty;
    }

    public void setNameProperty(String nameProperty) {
        this.nameProperty.set(nameProperty);
    }
}

class TypesViewModel {

}

public class MvvmTest {
    public static void main(String[] args) {
        Type type = new Type();
        TypeViewModel typeViewModel = new TypeViewModel(type);
        typeViewModel.setIdProperty(10);
        typeViewModel.setNameProperty("fire");

        System.out.println(type.getId());

        type.setId(20);

        System.out.println(type.getId());
        System.out.println(typeViewModel.getIdProperty());
    }
}
