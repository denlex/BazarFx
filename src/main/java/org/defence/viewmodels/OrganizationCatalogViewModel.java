package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import org.defence.domain.entities.Organization;
import org.defence.infrastructure.DbHelper;

import java.util.List;
import java.util.Optional;

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
	private Command deleteCommand;

	public OrganizationCatalogViewModel() {
		deleteCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Удаление организации");
				alert.setHeaderText(null);
				alert.setContentText("Вы действительно хотите удалить тип:\nНаименование:   " +
						getSelectedOrganization().getName());

				ButtonType yes = new ButtonType("Удалить");
				ButtonType no = new ButtonType("Отмена");

				alert.getButtonTypes().setAll(yes, no);
				((Button) alert.getDialogPane().lookupButton(yes)).setDefaultButton(true);

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == yes) {
					dbHelper.deleteOrganization(getSelectedOrganization().getId());
					organizations.getValue().removeIf(p -> p.getId() == getSelectedOrganization().getId());
				}
			}
		});

		loadAllOrganizations();
	}

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

	public Command getDeleteCommand() {
		return deleteCommand;
	}

	public void setDeleteCommand(Command deleteCommand) {
		this.deleteCommand = deleteCommand;
	}

	public void loadAllOrganizations() {
		List<Organization> organizationList = dbHelper.getAllOrganizations();

		for (Organization organization : organizationList) {
			organizations.add(new OrganizationViewModel(organization));
		}
	}
}
