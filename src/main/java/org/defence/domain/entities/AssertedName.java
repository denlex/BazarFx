package org.defence.domain.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 22.07.15.
 */
public class AssertedName implements IEntity {
    private int id;
    private String name;
    private String code;
	private List<CatalogDescription> catalogDescriptions = new ArrayList<>();

	public AssertedName() {
	}

	public AssertedName(String code, String name) {
		this(code, name, null);
	}

	public AssertedName(String code, String name, List<CatalogDescription> catalogDescriptions) {
		this.code = code;
		this.name = name;
		this.catalogDescriptions = catalogDescriptions;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	public List<CatalogDescription> getCatalogDescriptions() {
		return catalogDescriptions;
	}

	public void setCatalogDescriptions(List<CatalogDescription> catalogDescriptions) {
		this.catalogDescriptions = catalogDescriptions;
	}

	@Override
	public String toString() {
		return String.format("%s. %s", code, name);
	}
}
