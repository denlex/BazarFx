package org.defence.domain.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

/**
 * Created by root on 22.07.15.
 */
public class RegistrationInfo {
    private IntegerProperty id;
    private StringProperty applicationName;
    private ObjectProperty<LocalDateTime> registrationDate;
    private StringProperty registrationNumber;

    public RegistrationInfo() {
        id = new SimpleIntegerProperty();
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

    public String getApplicationName() {
        return applicationName.get();
    }

    public StringProperty applicationNameProperty() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName.set(applicationName);
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate.get();
    }

    public ObjectProperty<LocalDateTime> registrationDateProperty() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate.set(registrationDate);
    }

    public String getRegistrationNumber() {
        return registrationNumber.get();
    }

    public StringProperty registrationNumberProperty() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber.set(registrationNumber);
    }
}
