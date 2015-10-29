package org.defence.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 22.07.15.
 */
public class CatalogDescription implements Serializable {
	private int id;
	private String code;
	private String name;
	private RegistrationInfo registrationInfo;
	private Organization organization;
	private List<CharacteristicValue> values = new ArrayList<>();

	public CatalogDescription() {
	}

	public CatalogDescription(String code, String name) {
		this(code, name, new ArrayList<>());
	}

	public CatalogDescription(String code, String name, List<CharacteristicValue> values) {
		this.code = code;
		this.name = name;
		this.values = values;
	}

	public CatalogDescription(String code, String name, List<CharacteristicValue> values, Organization organization,
			RegistrationInfo info) {
		this(code, name, values);
		this.organization = organization;
		this.registrationInfo = info;
	}

	public CatalogDescription(Integer id, String code, String name, List<CharacteristicValue> values, Organization organization,
			RegistrationInfo info) {
		this(code, name, values, organization, info);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RegistrationInfo getRegistrationInfo() {
		return registrationInfo;
	}

	public void setRegistrationInfo(RegistrationInfo registrationInfo) {
		this.registrationInfo = registrationInfo;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<CharacteristicValue> getValues() {
		return values;
	}

	public void setValues(List<CharacteristicValue> values) {
		this.values = values;
	}
}
