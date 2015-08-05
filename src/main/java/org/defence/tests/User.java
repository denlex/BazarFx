package org.defence.tests;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by root on 8/5/15.
 */
public class User {
    private final static String USERNAME_PROP_NAME = "userName";
    private final ReadOnlyStringWrapper userName;
    private final static String PASSWORD_PROP_NAME = "password";

    private final StringProperty password;

    public User() {
        userName = new ReadOnlyStringWrapper(this, USERNAME_PROP_NAME, System.getProperty("user.name"));
        password = new SimpleStringProperty(this, PASSWORD_PROP_NAME, "");
    }

    public String getUserName() {
        return userName.get();
    }

    public ReadOnlyStringWrapper userNameProperty() {
        return userName;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }
}
