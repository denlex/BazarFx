package org.defence.domain.entities.tests;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 8/7/15.
 */
public class Certificate implements Serializable {
    private int id;
    private String name;
    private Set<Employee> employeeSet = new HashSet<Employee>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployeeSet() {
        return employeeSet;
    }

    public void setEmployeeSet(Set<Employee> employeeSet) {
        this.employeeSet = employeeSet;
    }
}
