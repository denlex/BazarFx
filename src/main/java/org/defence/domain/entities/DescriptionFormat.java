package org.defence.domain.entities;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 22.07.15.
 */
public class DescriptionFormat {
    private int id;
    private String name;
    private String code;
    private Set<CharacteristicKit> characteristicKits = new HashSet<CharacteristicKit>();
    private Set<CatalogDescription> catalogDescriptions = new HashSet<CatalogDescription>();

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<CharacteristicKit> getCharacteristicKits() {
        return characteristicKits;
    }

    public void setCharacteristicKits(Set<CharacteristicKit> characteristicKits) {
        this.characteristicKits = characteristicKits;
    }

    public Set<CatalogDescription> getCatalogDescriptions() {
        return catalogDescriptions;
    }

    public void setCatalogDescriptions(Set<CatalogDescription> catalogDescriptions) {
        this.catalogDescriptions = catalogDescriptions;
    }
}
