package org.defence.tests.treeview_demo;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 * Created by root on 9/15/15.
 */
public class City {
	StringProperty name = new SimpleStringProperty();
	ListProperty<Client> clients = new SimpleListProperty<>();

	public City() {
	}

	public City(String name) {
		this.name.setValue(name);
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

	public ObservableList<Client> getClients() {
		return clients.get();
	}

	public ListProperty<Client> clientsProperty() {
		return clients;
	}

	public void setClients(ObservableList<Client> clients) {
		this.clients.set(clients);
	}

	@Override
	public String toString() {
		return name.getValue();
	}
}
