package org.defence.viewmodels;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.defence.domain.entities.CatalogDescription;
import org.defence.domain.entities.CharacteristicValue;
import org.defence.domain.entities.Organization;
import org.defence.domain.entities.RegistrationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/15/15.
 */
public class CatalogDescriptionViewModel {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty code = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final ObjectProperty<RegistrationInfoViewModel> registrationInfo = new SimpleObjectProperty<>();
	private final ObjectProperty<OrganizationViewModel> organization = new SimpleObjectProperty<>();
	private final ListProperty<CharacteristicValueViewModel> values = new SimpleListProperty<>();

	public CatalogDescriptionViewModel() {
	}

	public CatalogDescriptionViewModel(Integer id, String code, String name, RegistrationInfo registrationInfo,
			Organization organization, List<CharacteristicValue> values) {
		this.id.setValue(id);
		this.code.setValue(code);
		this.name.setValue(name);
		this.registrationInfo.setValue(new RegistrationInfoViewModel(registrationInfo));
		this.organization.setValue(new OrganizationViewModel(organization));

		if (values != null) {
			List<CharacteristicValueViewModel> list = new ArrayList<>();
			for (CharacteristicValue value : values) {
				//TODO: Возникает непонятная ситуация - грузит список из двух value, а по факту один из элементов = null
				if (value == null) {
					continue;
				}
				list.add(new CharacteristicValueViewModel(value));
			}
			this.values.setValue(FXCollections.observableArrayList(list));
		}
	}

	public CatalogDescriptionViewModel(CatalogDescription catalogDescription) {
		this(catalogDescription.getId(), catalogDescription.getCode(), catalogDescription.getName(),
				catalogDescription.getRegistrationInfo(), catalogDescription.getOrganization(),
				catalogDescription.getValues());
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

	public RegistrationInfoViewModel getRegistrationInfo() {
		return registrationInfo.get();
	}

	public ObjectProperty<RegistrationInfoViewModel> registrationInfoProperty() {
		return registrationInfo;
	}

	public void setRegistrationInfo(RegistrationInfoViewModel registrationInfo) {
		this.registrationInfo.set(registrationInfo);
	}

	public OrganizationViewModel getOrganization() {
		return organization.get();
	}

	public ObjectProperty<OrganizationViewModel> organizationProperty() {
		return organization;
	}

	public void setOrganization(OrganizationViewModel organization) {
		this.organization.set(organization);
	}

	public ObservableList<CharacteristicValueViewModel> getValues() {
		return values.get();
	}

	public ListProperty<CharacteristicValueViewModel> valuesProperty() {
		return values;
	}

	public void setValues(ObservableList<CharacteristicValueViewModel> values) {
		this.values.set(values);
	}

	@Override
	public String toString() {
		return String.format("%s", name.getValue());
	}
}
