package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.defence.infrastructure.DbHelper;

/**
 * Created by root on 10/22/15.
 */
public class OrganizationEditViewModel implements ViewModel {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty code = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty type = new SimpleStringProperty();

	private final DbHelper dbHelper = DbHelper.getInstance();

	private OrganizationViewModel editedOrganization;

	private OrganizationCatalogViewModel parentViewModel;
	private Command saveCommand;

	public OrganizationEditViewModel() {
		saveCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				OrganizationViewModel organization = parentViewModel.getSelectedOrganization();

				if (id.getValue() != 0) {
					editedOrganization = new OrganizationViewModel(dbHelper.updateOrganization(id.getValue(), code
							.getValue(), name.getValue(), type.getValue()));

					parentViewModel.getSelectedOrganization().setCode(code.getValue());
					parentViewModel.getSelectedOrganization().setName(name.getValue());
					parentViewModel.getSelectedOrganization().setType(type.getValue());
				} else {
					editedOrganization = new OrganizationViewModel(dbHelper.addOrganization(code.getValue(), name
							.getValue(), type.getValue()));

					parentViewModel.getOrganizations().add(editedOrganization);
				}
			}
		});
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

	public Command getSaveCommand() {
		return saveCommand;
	}

	public void setSaveCommand(Command saveCommand) {
		this.saveCommand = saveCommand;
	}

	public OrganizationCatalogViewModel getParentViewModel() {
		return parentViewModel;
	}

	public void setParentViewModel(OrganizationCatalogViewModel parentViewModel) {
		this.parentViewModel = parentViewModel;
	}
}
