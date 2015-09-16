package org.defence.tests.treeview_demo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by root on 9/15/15.
 */
public class Car {
	StringProperty name = new SimpleStringProperty();
	IntegerProperty year = new SimpleIntegerProperty();

	public Car() {
	}

	public Car(String name, Integer year) {
		this.name.setValue(name);
		this.year.setValue(year);
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

	public int getYear() {
		return year.get();
	}

	public IntegerProperty yearProperty() {
		return year;
	}

	public void setYear(int year) {
		this.year.set(year);
	}

	@Override
	public String toString() {
		return name.getValue() + "  " + year.getValue();
	}
}
