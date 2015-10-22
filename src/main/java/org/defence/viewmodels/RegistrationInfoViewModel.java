package org.defence.viewmodels;

import javafx.beans.property.*;
import org.defence.domain.entities.RegistrationInfo;

import java.util.Date;

/**
 * Created by root on 10/22/15.
 */
public class RegistrationInfoViewModel {
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty applicationNumber = new SimpleStringProperty();
	private ObjectProperty<Date> registrationDate = new SimpleObjectProperty<>();
	private StringProperty registrationNumber = new SimpleStringProperty();

	public RegistrationInfoViewModel() {}

	public RegistrationInfoViewModel(RegistrationInfo info) {

	}

	public RegistrationInfoViewModel(IntegerProperty id, StringProperty applicationNumber, ObjectProperty<Date>
			registrationDate, StringProperty registrationNumber) {
		this.id = id;
		this.applicationNumber = applicationNumber;
		this.registrationDate = registrationDate;
		this.registrationNumber = registrationNumber;
	}
}
