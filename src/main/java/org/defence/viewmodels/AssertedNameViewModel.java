package org.defence.viewmodels;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.defence.domain.entities.AssertedName;
import org.defence.domain.entities.CatalogDescription;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 8/19/15.
 */
public class AssertedNameViewModel extends AbstractViewModel<AssertedName> {

	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty code = new SimpleStringProperty();
	private final ListProperty<CatalogDescriptionViewModel> catalogDescriptions = new SimpleListProperty<>();


	public AssertedNameViewModel() {
	}

	public AssertedNameViewModel(Integer id, String code, String name, List<CatalogDescription> catalogDescriptions) {
		this.id.setValue(id);
		this.name.setValue(name);
		this.code.setValue(code);

		if (catalogDescriptions != null) {
			List<CatalogDescriptionViewModel> list = new ArrayList<>();
			for (CatalogDescription description : catalogDescriptions) {
				list.add(new CatalogDescriptionViewModel(description));
			}
			this.catalogDescriptions.setValue(FXCollections.observableArrayList(list));
		}
	}

	public AssertedNameViewModel(AssertedName assertedName) {
		this(assertedName.getId(), assertedName.getCode(), assertedName.getName(), assertedName
				.getCatalogDescriptions());
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

	public ObservableList<CatalogDescriptionViewModel> getCatalogDescriptions() {
		return catalogDescriptions.get();
	}

	public ListProperty<CatalogDescriptionViewModel> catalogDescriptionsProperty() {
		return catalogDescriptions;
	}

	public void setCatalogDescriptions(ObservableList<CatalogDescriptionViewModel> catalogDescriptions) {
		this.catalogDescriptions.set(catalogDescriptions);
	}

	@Override
	public String toString() {
		return String.format("%s. %s", code.getValue(), name.getValue());
	}
}
