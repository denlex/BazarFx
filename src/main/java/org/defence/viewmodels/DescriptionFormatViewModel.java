package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import org.defence.domain.entities.AssertedName;
import org.defence.domain.entities.Characteristic;
import org.defence.domain.entities.DescriptionFormat;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by root on 9/11/15.
 */
public class DescriptionFormatViewModel implements ViewModel {
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty code = new SimpleStringProperty();
	private StringProperty name = new SimpleStringProperty();
	private SetProperty<CharacteristicViewModel> characteristics = new SimpleSetProperty<>();
	private SetProperty<AssertedNameViewModel> assertedNames = new SimpleSetProperty<>();

	public DescriptionFormatViewModel() {
	}

	public DescriptionFormatViewModel(String code, String name) {
		this(null, code, name, null, null);
	}

	public DescriptionFormatViewModel(Integer id, String code, String name, Set<Characteristic> characteristics,
			Set<AssertedName> assertedNames) {
		this.id.setValue(id);
		this.code.setValue(code);
		this.name.setValue(name);

		if (characteristics != null) {
			Set<CharacteristicViewModel> set = new LinkedHashSet<>();
			for (Characteristic characteristic : characteristics) {
				set.add(new CharacteristicViewModel(characteristic));
			}
			this.characteristics.setValue(FXCollections.observableSet(set));
		}

		if (assertedNames != null) {
			Set<AssertedNameViewModel> set = new LinkedHashSet<>();
			for (AssertedName assertedName : assertedNames) {
				set.add(new AssertedNameViewModel(assertedName));
			}
			this.assertedNames.setValue(FXCollections.observableSet(set));
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

	public ObservableSet<CharacteristicViewModel> getCharacteristics() {
		return characteristics.get();
	}

	public SetProperty<CharacteristicViewModel> characteristicsProperty() {
		return characteristics;
	}

	public void setCharacteristics(ObservableSet<CharacteristicViewModel> characteristics) {
		this.characteristics.set(characteristics);
	}

	public ObservableSet<AssertedNameViewModel> getAssertedNames() {
		return assertedNames.get();
	}

	public SetProperty<AssertedNameViewModel> assertedNamesProperty() {
		return assertedNames;
	}

	public void setAssertedNames(ObservableSet<AssertedNameViewModel> assertedNames) {
		this.assertedNames.set(assertedNames);
	}

	@Override
	public String toString() {
		return String.format("%s. %s", code.getValue(), name.getValue());
	}
}