package org.defence.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 22.07.15.
 */
public class CatalogDescription implements Serializable {
    private int id;
    private String name;
    private List<CharacteristicValue> values = new ArrayList<CharacteristicValue>();

    public CatalogDescription() {
    }

    public CatalogDescription(String name) {
        this.name = name;
    }

    public CatalogDescription(String name, List<CharacteristicValue> values) {
        this.name = name;
        this.values = values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CharacteristicValue> getValues() {
        return values;
    }

    public void setValues(List<CharacteristicValue> values) {
        this.values = values;
    }
}
