package org.aliensource.symptommanagement.cloud.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

/**
 * Created by ttruong on 13-Nov-14.
 */
@Entity
public class Doctor extends Person {

    //many-to-many relationship mapped by the owner's / Patient's attribute doctors
    @ManyToMany(fetch = FetchType.EAGER)
    protected List<Patient> patients = new ArrayList<Patient>();

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    @Override
    //workaround otherwise JSON conversion complaints
    //override roles and define attribute for conversion
    @JsonProperty(value = "doctorRoles")
    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public int hashCode() {
        // Google Guava provides great utilities for hashing
        return Objects.hashCode(
                firstName,
                lastName,
                username,
                password,
                dateOfBirth,
                patients);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Doctor) {
            Doctor other = (Doctor) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(firstName, other.firstName)
                    && Objects.equal(lastName, other.lastName)
                    && Objects.equal(username, other.username)
                    && Objects.equal(password, other.password)
                    && Objects.equal(dateOfBirth, other.dateOfBirth)
                    && Objects.equal(patients, other.patients);
        } else {
            return false;
        }
    }
}
