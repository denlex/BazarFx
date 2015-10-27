package org.defence.domain.entities;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 22.07.15.
 */
public class CatalogClass {
    private int id;
    private String code;
    private String name;
    private String include;
    private String exclude;
	private List<DescriptionFormat> formats = new LinkedList<>();

    public CatalogClass() {

	}

	public CatalogClass(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public CatalogClass(Integer id, String code, String name) {
		this(code, name);
		this.id = id;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

	public List<DescriptionFormat> getFormats() {
		return formats;
	}

	public void setFormats(List<DescriptionFormat> formats) {
		this.formats = formats;
	}
}
