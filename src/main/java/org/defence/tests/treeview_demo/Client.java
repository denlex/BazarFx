package org.defence.tests.treeview_demo;

import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.util.Set;

/**
 * Created by root on 9/15/15.
 */
public class Client {
	StringProperty name = new SimpleStringProperty();
	SetProperty<Car> cars = new SimpleSetProperty<>();

	public Client() {
	}

	public Client(String name, Set<Car> cars) {
		this.name.setValue(name);
		this.cars.set(FXCollections.observableSet(cars));
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

	public ObservableSet<Car> getCars() {
		return cars.get();
	}

	public SetProperty<Car> carsProperty() {
		return cars;
	}

	public void setCars(ObservableSet<Car> cars) {
		this.cars.set(cars);
	}

	@Override
	public String toString() {
		return name.getValue();
	}
}
