package org.defence.tests;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by root on 24.07.15.
 */

class Contact {
    private StringProperty firstName;
    private StringProperty lastName;

    public Contact(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
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
}

public class MainTest {

    public static void main(String[] args) {
        Contact contact = new Contact("Joe", "Dawson");
        StringProperty fname = new SimpleStringProperty();
        fname.bindBidirectional(contact.firstNameProperty());

        contact.firstNameProperty().set("Play");
        fname.set("Jane");

        System.out.println("contact.firstNameProperty = " + contact.firstNameProperty().get());
        System.out.println("fname = " + fname.get());
    }
}