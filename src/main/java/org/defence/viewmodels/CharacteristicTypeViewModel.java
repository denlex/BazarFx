package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.defence.domain.entities.Characteristic;
import org.defence.domain.entities.CharacteristicType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 06.09.15.
 */
public class CharacteristicTypeViewModel implements ViewModel {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty code = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final ListProperty<CharacteristicViewModel> characteristics = new SimpleListProperty<>();

    public CharacteristicTypeViewModel() {}

	public CharacteristicTypeViewModel(Integer id, String code, String name, List<Characteristic> characteristics) {
		this.id.setValue(id);
		this.code.setValue(code);
		this.name.setValue(name);

		if (characteristics != null) {
			List<CharacteristicViewModel> list = new ArrayList<>();
			for (Characteristic characteristic : characteristics) {
				list.add(new CharacteristicViewModel(characteristic));
			}
			this.characteristics.setValue(FXCollections.observableList(list));
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

    public ObservableList<CharacteristicViewModel> getCharacteristics() {
        return characteristics.get();
    }

    public ListProperty<CharacteristicViewModel> characteristicsProperty() {
        return characteristics;
    }

    public void setCharacteristics(ObservableList<CharacteristicViewModel> characteristics) {
        this.characteristics.set(characteristics);
    }
}
