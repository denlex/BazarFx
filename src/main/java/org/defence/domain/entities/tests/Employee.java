package org.defence.domain.entities.tests;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 8/7/15.
 */
public class Employee implements Serializable {
    private int id;
    private String fio;
    private Set<Certificate> certificateSet = new HashSet<Certificate>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Set<Certificate> getCertificateSet() {
        return certificateSet;
    }

    public void setCertificateSet(Set<Certificate> certificateSet) {
        this.certificateSet = certificateSet;
    }
}
