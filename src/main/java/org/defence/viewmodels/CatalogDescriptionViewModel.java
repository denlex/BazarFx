package org.defence.viewmodels;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.defence.domain.entities.CatalogDescription;
import org.defence.domain.entities.CharacteristicValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/15/15.
 */
public class CatalogDescriptionViewModel {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final ListProperty<CharacteristicValueViewModel> values = new SimpleListProperty<>();

	public CatalogDescriptionViewModel() {
	}

	public CatalogDescriptionViewModel(Integer id, String name, List<CharacteristicValue> values) {
		this.id.setValue(id);
		this.name.setValue(name);

		if (values != null) {
			List<CharacteristicValueViewModel> list = new ArrayList<>();
			for (CharacteristicValue value : values) {
				list.add(new CharacteristicValueViewModel(value));
			}
			this.values.setValue(FXCollections.observableArrayList(list));
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

	public ObservableList<CharacteristicValueViewModel> getValues() {
		return values.get();
	}

	public ListProperty<CharacteristicValueViewModel> valuesProperty() {
		return values;
	}

	public void setValues(ObservableList<CharacteristicValueViewModel> values) {
		this.values.set(values);
	}

	@Override
	public String toString() {
		return String.format("%s", name.getValue());
	}
}
