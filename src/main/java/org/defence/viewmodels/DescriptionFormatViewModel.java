package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.defence.domain.entities.AssertedName;
import org.defence.domain.entities.Characteristic;
import org.defence.domain.entities.DescriptionFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/11/15.
 */
public class DescriptionFormatViewModel implements ViewModel {
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty code = new SimpleStringProperty();
	private StringProperty name = new SimpleStringProperty();
	private ListProperty<CharacteristicViewModel> characteristics = new SimpleListProperty<>();
	private ListProperty<AssertedNameViewModel> assertedNames = new SimpleListProperty<>();

	public DescriptionFormatViewModel() {
	}

	public DescriptionFormatViewModel(String code, String name) {
		this(null, code, name, null, null);
	}

	public DescriptionFormatViewModel(Integer id, String code, String name, List<Characteristic> characteristics,
			List<AssertedName> assertedNames) {
		this.id.setValue(id);
		this.code.setValue(code);
		this.name.setValue(name);

		if (characteristics != null) {
			List<CharacteristicViewModel> list = new ArrayList<>();
			for (Characteristic characteristic : characteristics) {
				list.add(new CharacteristicViewModel(characteristic));
			}
			this.characteristics.setValue(FXCollections.observableArrayList(list));
		}

		if (assertedNames != null) {
			List<AssertedNameViewModel> list = new ArrayList<>();
			for (AssertedName assertedName : assertedNames) {
				list.add(new AssertedNameViewModel(assertedName));
			}
			this.assertedNames.setValue(FXCollections.observableArrayList(list));
		}
	}

	public DescriptionFormatViewModel(DescriptionFormat format) {
		this(format.getId(), format.getCode(), format.getName(), format.getCharacteristics(), format
				.getAssertedNames());
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

	public String getCode() {
		return code.get();
	}

	public StringProperty codeProperty() {
		return code;
	}

	public void setCode(String code) {
		this.code.set(code);
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

	public ObservableList<CharacteristicViewModel> getCharacteristics() {
		return characteristics.get();
	}

	public ListProperty<CharacteristicViewModel> characteristicsProperty() {
		return characteristics;
	}

	public void setCharacteristics(ObservableList<CharacteristicViewModel> characteristics) {
		this.characteristics.set(characteristics);
	}

	public ObservableList<AssertedNameViewModel> getAssertedNames() {
		return assertedNames.get();
	}

	public ListProperty<AssertedNameViewModel> assertedNamesProperty() {
		return assertedNames;
	}

	public void setAssertedNames(ObservableList<AssertedNameViewModel> assertedNames) {
		this.assertedNames.set(assertedNames);
	}

	@Override
	public String toString() {
		return String.format("%s. %s", code.getValue(), name.getValue());
	}
}