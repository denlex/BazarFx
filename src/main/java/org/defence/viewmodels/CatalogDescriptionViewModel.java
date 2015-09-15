package org.defence.viewmodels;

import javafx.beans.property.*;
import javafx.collections.ObservableSet;

/**
 * Created by root on 9/15/15.
 */
public class CatalogDescriptionViewModel {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final SetProperty<CharacteristicValueViewModel> values = new SimpleSetProperty<>();

	public int getId() {
		return id.get();
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
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

	public ObservableSet<CharacteristicValueViewModel> getValues() {
		return values.get();
	}

	public SetProperty<CharacteristicValueViewModel> valuesProperty() {
		return values;
	}

	public void setValues(ObservableSet<CharacteristicValueViewModel> values) {
		this.values.set(values);
	}
}
