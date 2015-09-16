package org.defence.viewmodels;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import org.defence.domain.entities.CatalogDescription;
import org.defence.domain.entities.CharacteristicValue;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 9/15/15.
 */
public class CatalogDescriptionViewModel {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final SetProperty<CharacteristicValueViewModel> values = new SimpleSetProperty<>();

	public CatalogDescriptionViewModel() {
	}

	public CatalogDescriptionViewModel(CatalogDescription catalogDescription) {
		id.setValue(catalogDescription.getId());
		name.setValue(catalogDescription.getName());

		if (catalogDescription.getValues() != null) {
			Set<CharacteristicValueViewModel> set = new HashSet<>();
			for (CharacteristicValue value : catalogDescription.getValues()) {
				set.add(new CharacteristicValueViewModel(value));
			}
			values.setValue(FXCollections.observableSet(set));
		}
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

	@Override
	public String toString() {
		return String.format("%s", name.getValue());
	}
}
