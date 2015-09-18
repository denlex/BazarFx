package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import org.defence.domain.entities.AssertedName;
import org.defence.domain.entities.CharacteristicKit;
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
	private SetProperty<CharacteristicKitViewModel> characteristicKits = new SimpleSetProperty<>();
	private SetProperty<AssertedNameViewModel> assertedNames = new SimpleSetProperty<>();

	public DescriptionFormatViewModel() {
	}

	public DescriptionFormatViewModel(String code, String name) {
		this(null, code, name, null, null);
	}

	public DescriptionFormatViewModel(Integer id, String code, String name, Set<CharacteristicKit> characteristicKits,
			Set<AssertedName> assertedNames) {
		this.id.setValue(id);
		this.code.setValue(code);
		this.name.setValue(name);

		if (characteristicKits != null) {
			Set<CharacteristicKitViewModel> set = new LinkedHashSet<>();
			for (CharacteristicKit kit : characteristicKits) {
				set.add(new CharacteristicKitViewModel(kit));
			}
			this.characteristicKits.setValue(FXCollections.observableSet(set));
		}

		/*if (assertedNames != null) {
			Set<AssertedNameViewModel> set = new HashSet<>();
			for (AssertedName assertedName : assertedNames) {
				set.add(new AssertedNameViewModel(assertedName));
			}
			this.assertedNames.setValue(FXCollections.observableSet(set));
		}*/
		if (assertedNames != null) {
			Set<AssertedNameViewModel> set = new LinkedHashSet<>();
			for (AssertedName assertedName : assertedNames) {
				set.add(new AssertedNameViewModel(assertedName));
			}
			this.assertedNames.setValue(FXCollections.observableSet(set));
		}
	}

	public DescriptionFormatViewModel(DescriptionFormat format) {
		this(format.getId(), format.getCode(), format.getName(), format.getCharacteristicKits(), format
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

	public ObservableSet<CharacteristicKitViewModel> getCharacteristicKits() {
		return characteristicKits.get();
	}

	public SetProperty<CharacteristicKitViewModel> characteristicKitsProperty() {
		return characteristicKits;
	}

	public void setCharacteristicKits(ObservableSet<CharacteristicKitViewModel> characteristicKits) {
		this.characteristicKits.set(characteristicKits);
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