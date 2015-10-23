package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.defence.infrastructure.DbHelper;

/**
 * Created by root on 10/22/15.
 */
public class OrganizationEditViewModel implements ViewModel {
	private final StringProperty code = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();

	private final DbHelper dbHelper = DbHelper.getInstance();

	private OrganizationCatalogViewModel parentViewModel;
	private Command saveCommand;

	public OrganizationEditViewModel() {
		saveCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {

			}
		});
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
}
