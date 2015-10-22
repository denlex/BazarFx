package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.defence.infrastructure.DbHelper;

/**
 * Created by root on 10/22/15.
 */
public class OrganizationCatalogViewModel implements ViewModel {
	private final StringProperty code = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();

	private ObjectProperty<OrganizationViewModel> selectedOrganization = new SimpleObjectProperty<>();

	private ListProperty<OrganizationViewModel> organizations = new SimpleListProperty<>(FXCollections
			.observableArrayList());

	private final DbHelper dbHelper = DbHelper.getInstance();

	public OrganizationViewModel getSelectedOrganization() {
		return selectedOrganization.get();
	}

	public ObjectProperty<OrganizationViewModel> selectedOrganizationProperty() {
		return selectedOrganization;
	}

	public void setSelectedOrganization(OrganizationViewModel selectedOrganization) {
		this.selectedOrganization.set(selectedOrganization);
	}

	public ObservableList<OrganizationViewModel> getOrganizations() {
		return organizations.get();
	}

	public ListProperty<OrganizationViewModel> organizationsProperty() {
		return organizations;
	}

	public void setOrganizations(ObservableList<OrganizationViewModel> organizations) {
		this.organizations.set(organizations);
	}
}
