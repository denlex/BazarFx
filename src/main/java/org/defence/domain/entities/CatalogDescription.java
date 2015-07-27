package org.defence.domain.entities;

/**
 * Created by root on 22.07.15.
 */
public class CatalogDescription {
    private int id;
    private String federalNumber;
    private String name;
    private DescriptionFormat descriptionFormat;
    private AssertedName assertedName;
    private RegistrationInfo registrationInfo;
    private CharacteristicList characteristicList;
    private Organization developerOrganization;
    private Organization ratifyingOrganization;
    private Organization supplierOrganization;
}
