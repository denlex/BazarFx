package org.defence.viewmodels;

import com.sun.javafx.collections.ObservableSetWrapper;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.ObservableSet;
import org.defence.domain.entities.AssertedName;
import org.defence.domain.entities.CharacteristicKit;
import org.defence.domain.entities.DescriptionFormat;

import java.util.Set;

/**
 * Created by root on 9/11/15.
 */
public class DescriptionFormatViewModel implements ViewModel {
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty code = new SimpleStringProperty();
	private StringProperty name = new SimpleStringProperty();
	private SetProperty<CharacteristicKit> characteristicKits = new SimpleSetProperty<>();
	private SetProperty<AssertedName> assertedNames = new SimpleSetProperty<>();

	public DescriptionFormatViewModel() {
	}

	public DescriptionFormatViewModel(String code, String name) {
		this(code, name, null, null);
	}

	public DescriptionFormatViewModel(String code, String name, Set<CharacteristicKit> characteristicKits,
			Set<AssertedName> assertedNames) {
		this.code.setValue(code);
		this.name.setValue(name);
		this.characteristicKits.setValue(new ObservableSetWrapper<>(characteristicKits));
		this.assertedNames.setValue(new ObservableSetWrapper<>(assertedNames));
	}

	public DescriptionFormatViewModel(DescriptionFormat format) {
		id.setValue(format.getId());
		code.setValue(format.getCode());
		name.setValue(format.getName());
		characteristicKits.setValue(new ObservableSetWrapper<>(format.getCharacteristicKits()));
		assertedNames.setValue(new ObservableSetWrapper<>(format.getAssertedNames()));
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

	public ObservableSet<CharacteristicKit> getCharacteristicKits() {
		return characteristicKits.get();
	}

	public SetProperty<CharacteristicKit> characteristicKitsProperty() {
		return characteristicKits;
	}

	public void setCharacteristicKits(ObservableSet<CharacteristicKit> characteristicKits) {
		this.characteristicKits.set(characteristicKits);
	}

	public ObservableSet<AssertedName> getAssertedNames() {
		return assertedNames.get();
	}

	public SetProperty<AssertedName> assertedNamesProperty() {
		return assertedNames;
	}

	public void setAssertedNames(ObservableSet<AssertedName> assertedNames) {
		this.assertedNames.set(assertedNames);
	}

	@Override
	public String toString() {
		return name.getValue();
	}
}