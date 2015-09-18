package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import org.defence.domain.entities.Characteristic;
import org.defence.domain.entities.CharacteristicKit;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by root on 9/10/15.
 */
public class CharacteristicKitViewModel implements ViewModel {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final BooleanProperty isBelong = new SimpleBooleanProperty();
    private final SetProperty<CharacteristicViewModel> characteristics = new SimpleSetProperty<>();

    public CharacteristicKitViewModel() {

    }

	public CharacteristicKitViewModel(Integer id, String name, Set<Characteristic> characteristics) {
		this.setId(id);
		this.setName(name);

		if (characteristics != null) {
			Set<CharacteristicViewModel> set = new LinkedHashSet<>();
			for (Characteristic characteristic : characteristics) {
				set.add(new CharacteristicViewModel(characteristic));
			}
			this.characteristics.setValue(FXCollections.observableSet(set));
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

	public ObservableSet<CharacteristicViewModel> getCharacteristics() {
		return characteristics.get();
	}

	public SetProperty<CharacteristicViewModel> characteristicsProperty() {
		return characteristics;
	}

	public void setCharacteristics(ObservableSet<CharacteristicViewModel> characteristics) {
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
