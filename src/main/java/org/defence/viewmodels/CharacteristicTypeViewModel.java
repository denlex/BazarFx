package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import org.defence.domain.entities.Characteristic;
import org.defence.domain.entities.CharacteristicType;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by root on 06.09.15.
 */
public class CharacteristicTypeViewModel implements ViewModel {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty code = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final SetProperty<CharacteristicViewModel> characteristics = new SimpleSetProperty<>();

    public CharacteristicTypeViewModel() {}

	public CharacteristicTypeViewModel(Integer id, String code, String name, Set<Characteristic> characteristics) {
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
	}

    public CharacteristicTypeViewModel(CharacteristicType type) {
		this(type.getId(), type.getCode(), type.getName(), type.getCharacteristics());
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
}
