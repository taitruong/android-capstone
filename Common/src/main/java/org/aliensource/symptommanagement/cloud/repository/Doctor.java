package org.aliensource.symptommanagement.cloud.repository;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

/**
 * Created by ttruong on 13-Nov-14.
 */
@Entity
public class Doctor extends Person {

    //many-to-many relationship mapped by the owner's / Patient's attribute doctors
    @ManyToMany(mappedBy = "doctors", fetch = FetchType.EAGER)
    protected Collection<Patient> patients = new ArrayList<Patient>();

    public Collection<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Collection<Patient> patients) {
        this.patients = patients;
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

    /**
     * Two Videos are considered equal if they have exactly the same values for
     * their name, url, and duration.
     *
     */
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
