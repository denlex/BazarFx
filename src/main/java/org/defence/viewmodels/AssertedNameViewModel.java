package org.defence.viewmodels;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import org.defence.domain.entities.AssertedName;
import org.defence.domain.entities.CatalogDescription;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by root on 8/19/15.
 */
public class AssertedNameViewModel extends AbstractViewModel<AssertedName> {

	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty code = new SimpleStringProperty();
	private final SetProperty<CatalogDescriptionViewModel> catalogDescriptions = new SimpleSetProperty<>();


	public AssertedNameViewModel() {
	}

	public AssertedNameViewModel(Integer id, String code, String name, Set<CatalogDescription> catalogDescriptions) {
		this.id.setValue(id);
		this.name.setValue(name);
		this.code.setValue(code);

		if (catalogDescriptions != null) {
			Set<CatalogDescriptionViewModel> set = new LinkedHashSet<>();
			for (CatalogDescription description : catalogDescriptions) {
				set.add(new CatalogDescriptionViewModel(description));
			}
			this.catalogDescriptions.setValue(FXCollections.observableSet(set));
		}
	}

	public AssertedNameViewModel(AssertedName assertedName) {
		this(assertedName.getId(), assertedName.getCode(), assertedName.getName(), assertedName.getCatalogDescriptions());
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

	public String getCode() {
		return code.get();
	}

	public StringProperty codeProperty() {
		return code;
	}

	public void setCode(String code) {
		this.code.set(code);
	}

	public ObservableSet<CatalogDescriptionViewModel> getCatalogDescriptions() {
		return catalogDescriptions.get();
	}

	public SetProperty<CatalogDescriptionViewModel> catalogDescriptionsProperty() {
		return catalogDescriptions;
	}

	public void setCatalogDescriptions(ObservableSet<CatalogDescriptionViewModel> catalogDescriptions) {
		this.catalogDescriptions.set(catalogDescriptions);
	}

	@Override
	public String toString() {
		return String.format("%s. %s", code.getValue(), name.getValue());
	}
}
