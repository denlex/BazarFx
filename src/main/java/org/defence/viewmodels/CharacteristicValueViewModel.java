package org.defence.viewmodels;

import javafx.beans.property.*;
import org.defence.domain.entities.CharacteristicValue;

/**
 * Created by root on 9/15/15.
 */
public class CharacteristicValueViewModel {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final ObjectProperty<CharacteristicViewModel> characteristic = new SimpleObjectProperty<>();
	private final StringProperty value = new SimpleStringProperty();

	public CharacteristicValueViewModel() {
	}

	public CharacteristicValueViewModel(CharacteristicValue characteristicValue) {
		id.setValue(characteristicValue.getId());
		characteristic.setValue(new CharacteristicViewModel(characteristicValue.getCharacteristic()));
		value.setValue(characteristicValue.getValue());
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

	public CharacteristicViewModel getCharacteristic() {
		return characteristic.get();
	}

	public ObjectProperty<CharacteristicViewModel> characteristicProperty() {
		return characteristic;
	}

	public void setCharacteristic(CharacteristicViewModel characteristic) {
		this.characteristic.set(characteristic);
	}

	public String getValue() {
		return value.get();
	}

	public StringProperty valueProperty() {
		return value;
	}

	public void setValue(String value) {
		this.value.set(value);
	}

	@Override
	public String toString() {
		System.out.println("CharacteristicValueViewModel");

		if (id != null && value != null && characteristic != null) {
			return "id = " + id + "\nvalue = " + value + "\ncharacteristic = " + characteristic.getValue().getName();
		} else {
			return super.toString();
		}
	}
}
