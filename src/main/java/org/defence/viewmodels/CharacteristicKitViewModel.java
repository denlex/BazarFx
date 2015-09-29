package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.defence.domain.entities.Characteristic;
import org.defence.domain.entities.CharacteristicKit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/10/15.
 */
public class CharacteristicKitViewModel implements ViewModel {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final BooleanProperty isBelong = new SimpleBooleanProperty();
    private final ListProperty<CharacteristicViewModel> characteristics = new SimpleListProperty<>();

    public CharacteristicKitViewModel() {

    }

	public CharacteristicKitViewModel(Integer id, String name, List<Characteristic> characteristics) {
		this.setId(id);
		this.setName(name);

		if (characteristics != null) {
			List<CharacteristicViewModel> list = new ArrayList<>();
			for (Characteristic characteristic : characteristics) {
				list.add(new CharacteristicViewModel(characteristic));
			}
			this.characteristics.setValue(FXCollections.observableList(list));
		}
	}

    public CharacteristicKitViewModel(CharacteristicKit characteristicKit) {
		this(characteristicKit.getId(), characteristicKit.getName(), characteristicKit.getCharacteristics());
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

    public ObservableList<CharacteristicViewModel> getCharacteristics() {
        return characteristics.get();
    }

    public ListProperty<CharacteristicViewModel> characteristicsProperty() {
        return characteristics;
    }

    public void setCharacteristics(ObservableList<CharacteristicViewModel> characteristics) {
        this.characteristics.set(characteristics);
    }

    public boolean getIsBelong() {
        return isBelong.get();
    }

    public BooleanProperty isBelongProperty() {
        return isBelong;
    }

    public void setIsBelong(boolean isBelong) {
        this.isBelong.set(isBelong);
    }
}
