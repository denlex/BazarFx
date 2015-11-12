package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import org.defence.domain.entities.Organization;

/**
 * Created by root on 10/21/15.
 */
public class OrganizationViewModel implements ViewModel {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty code = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty type = new SimpleStringProperty();

	public OrganizationViewModel() {
	}

	public OrganizationViewModel(Integer id, String code, String name, String type) {
		this.id.setValue(id);
		this.code.setValue(code);
		this.name.setValue(name);
		this.type.setValue(type);
	}

	public OrganizationViewModel(Organization organization) {
		this(organization.getId(), organization.getCode(), organization.getName(), organization.getType());
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

	public String getType() {
		return type.get();
	}

	public StringProperty typeProperty() {
		return type;
	}

	public void setType(String type) {
		this.type.set(type);
	}

	@Override
	public String toString() {
		return name.getValue();
	}

	public Organization toModel() {
		Organization result = new Organization();
		result.setId(this.getId());
		result.setCode(this.getCode());
		result.setName(this.getName());
		result.setType(this.getType());

		return result;
	}
}
