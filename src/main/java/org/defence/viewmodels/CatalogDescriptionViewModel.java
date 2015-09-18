package org.defence.viewmodels;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import org.defence.domain.entities.CatalogDescription;
import org.defence.domain.entities.CharacteristicValue;

import java.util.LinkedHashSet;
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

	public CatalogDescriptionViewModel(Integer id, String name, Set<CharacteristicValue> values) {
		this.id.setValue(id);
		this.name.setValue(name);

		if (values != null) {
			Set<CharacteristicValueViewModel> set = new LinkedHashSet<>();
			for (CharacteristicValue value : values) {
				set.add(new CharacteristicValueViewModel(value));
			}
			this.values.setValue(FXCollections.observableSet(set));
		}
	}

	public CatalogDescriptionViewModel(CatalogDescription catalogDescription) {
		this(catalogDescription.getId(), catalogDescription.getName(), catalogDescription.getValues());
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
