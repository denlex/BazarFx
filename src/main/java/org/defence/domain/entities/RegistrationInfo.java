package org.defence.domain.entities;

import java.util.Date;

/**
 * Created by root on 22.07.15.
 */
public class RegistrationInfo {
    private int id;
    private String applicationName;
    private Date registrationDate;
    private String registrationNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
