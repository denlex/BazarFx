package org.defence.domain.entities;

import java.util.Date;

/**
 * Created by root on 22.07.15.
 */
public class RegistrationInfo {
    private int id;
    private String applicationNumber;
    private Date registrationDate;
    private String registrationNumber;

	public RegistrationInfo() {
	}

	public RegistrationInfo(String applicationNumber, String registrationNumber, Date registrationDate) {
		this.applicationNumber = applicationNumber;
		this.registrationNumber = registrationNumber;
		this.registrationDate = registrationDate;
	}

	public RegistrationInfo(Integer id, String applicationNumber, String registrationNumber, Date registrationDate) {
		this(applicationNumber, registrationNumber, registrationDate);
		this.id = id;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
}
