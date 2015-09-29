package org.defence.domain.entities;

import java.io.Serializable;

/**
 * Created by root on 08.08.15.
 */
public class CharacteristicValue implements Serializable {
    private int id;
    private Characteristic characteristic;
    private String value;

    public CharacteristicValue() {
    }

    public CharacteristicValue(Characteristic characteristic, String value) {
        this.characteristic = characteristic;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

	@Override
	public String toString() {
		System.out.println("CharacteristicValue");
		return super.toString();
	}
}
