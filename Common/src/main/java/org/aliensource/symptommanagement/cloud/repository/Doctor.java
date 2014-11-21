package org.aliensource.symptommanagement.cloud.repository;

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
    private Collection<Patient> patients;

    public Collection<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Collection<Patient> patients) {
        this.patients = patients;
    }
}
