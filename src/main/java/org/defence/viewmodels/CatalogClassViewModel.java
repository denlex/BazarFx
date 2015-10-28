package org.defence.viewmodels;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.defence.domain.entities.CatalogClass;
import org.defence.domain.entities.DescriptionFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/26/15.
 */
public class CatalogClassViewModel {
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty code = new SimpleStringProperty();
	private StringProperty name = new SimpleStringProperty();
	private StringProperty include = new SimpleStringProperty();
	private StringProperty exclude = new SimpleStringProperty();
	private ListProperty<DescriptionFormatViewModel> formats = new SimpleListProperty<>();

	public CatalogClassViewModel() {
	}

	public CatalogClassViewModel(CatalogClass catalogClass) {
		id.setValue(catalogClass.getId());
		code.setValue(catalogClass.getCode());
		name.setValue(catalogClass.getName());

		if (catalogClass.getFormats() != null) {
			List<DescriptionFormatViewModel> list = new ArrayList<>();
			for (DescriptionFormat format : catalogClass.getFormats()) {
				list.add(new DescriptionFormatViewModel(format));
			}
			this.formats.setValue(FXCollections.observableArrayList(list));
		}
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

	public ObservableList<DescriptionFormatViewModel> getFormats() {
		return formats.get();
	}

	public ListProperty<DescriptionFormatViewModel> formatsProperty() {
		return formats;
	}

	public void setFormats(ObservableList<DescriptionFormatViewModel> formats) {
		this.formats.set(formats);
	}

	@Override
	public String toString() {
		return String.format("%s. %s", code.getValue(), name.getValue());
	}
}
