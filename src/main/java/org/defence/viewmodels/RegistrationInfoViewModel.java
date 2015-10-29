package org.defence.viewmodels;

import javafx.beans.property.*;
import org.defence.domain.entities.RegistrationInfo;
import org.defence.tools.DateConverter;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by root on 10/22/15.
 */
public class RegistrationInfoViewModel {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty applicationNumber = new SimpleStringProperty();
	private final ObjectProperty<LocalDate> registrationDate = new SimpleObjectProperty<>();
	private final StringProperty registrationNumber = new SimpleStringProperty();

	public RegistrationInfoViewModel() {
	}

	public RegistrationInfoViewModel(RegistrationInfo info) {
		this(info.getId(), info.getApplicationNumber(), info.getRegistrationDate(), info.getRegistrationNumber());
	}

	public RegistrationInfoViewModel(Integer id, String applicationNumber, Date registrationDate, String registrationNumber) {
		this.id.setValue(id);
		this.applicationNumber.setValue(applicationNumber);


		this.registrationDate.setValue(DateConverter.toLocalDate(registrationDate));
		this.registrationNumber.setValue(registrationNumber);
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

	public String getApplicationNumber() {
		return applicationNumber.get();
	}

	public StringProperty applicationNumberProperty() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber.set(applicationNumber);
	}

	public LocalDate getRegistrationDate() {
		return registrationDate.get();
	}

	public ObjectProperty<LocalDate> registrationDateProperty() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
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

	public RegistrationInfo toModel() {
		RegistrationInfo result = new RegistrationInfo();
		result.setId(this.getId());
		result.setApplicationNumber(this.getApplicationNumber());
		result.setRegistrationNumber(this.getRegistrationNumber());
		result.setRegistrationDate(DateConverter.toDate(this.getRegistrationDate()));

		return result;
	}
}
