package org.defence.viewmodels;

import javafx.beans.property.*;
import javafx.collections.ObservableSet;
import org.defence.domain.entities.AssertedName;

/**
 * Created by root on 8/19/15.
 */
public class AssertedNameViewModel extends AbstractViewModel<AssertedName> {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty number = new SimpleStringProperty();
	private final SetProperty<CatalogDescriptionViewModel> catalogDescriptions = new SimpleSetProperty<>();

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

	public String getNumber() {
		return number.get();
	}

	public StringProperty numberProperty() {
		return number;
	}

	public void setNumber(String number) {
		this.number.set(number);
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
}
