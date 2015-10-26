package org.defence.viewmodels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by root on 10/26/15.
 */
public class CatalogClassViewModel {
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty number = new SimpleStringProperty();
	private StringProperty name = new SimpleStringProperty();
	private StringProperty include = new SimpleStringProperty();
	private StringProperty exclude = new SimpleStringProperty();

	public CatalogClassViewModel() {
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

	public String getNumber() {
		return number.get();
	}

	public StringProperty numberProperty() {
		return number;
	}

	public void setNumber(String number) {
		this.number.set(number);
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

	public String getInclude() {
		return include.get();
	}

	public StringProperty includeProperty() {
		return include;
	}

	public void setInclude(String include) {
		this.include.set(include);
	}

	public String getExclude() {
		return exclude.get();
	}

	public StringProperty excludeProperty() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude.set(exclude);
	}
}
