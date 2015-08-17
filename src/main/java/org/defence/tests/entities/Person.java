package org.defence.tests.entities;

import com.sun.javafx.collections.ObservableSetWrapper;
import javafx.beans.property.*;
import javafx.collections.ObservableSet;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by root on 8/14/15.
 */
public class Person implements Serializable {
    private IntegerProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private IntegerProperty age;
    private SetProperty<Certificate> certificates;

    public Person() {
        id = new SimpleIntegerProperty();
    }

    public Person(String firstName, String lastName, Integer age) {
        this(firstName, lastName, age, null);
    }

    public Person(String firstName, String lastName, Integer age, Set<Certificate> certificates) {
        this();
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.age = new SimpleIntegerProperty(age);
        this.certificates = new SimpleSetProperty<>(new ObservableSetWrapper<>(certificates));
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

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public ObservableSet<Certificate> getCertificates() {
        return certificates.get();
    }

    public SetProperty<Certificate> certificatesProperty() {
        return certificates;
    }

    public void setCertificates(ObservableSet<Certificate> certificates) {
        this.certificates.set(certificates);
    }
}
