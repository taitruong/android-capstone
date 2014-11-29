package org.aliensource.symptommanagement.cloud.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * Created by ttruong on 13-Nov-14.
 */
@Entity
public class Patient extends Person {

    protected String medicalRecordNumber;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST})
    //define join table with the Patient entity being the owner
    @JoinTable(
            name = "PATIENT_DOCTOR",
            joinColumns = @JoinColumn(name="patient_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    protected List<Doctor> doctors = new ArrayList<Doctor>();

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "patient",
            cascade = CascadeType.ALL)
    protected List<CheckIn> checkIns = new ArrayList<CheckIn>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    protected List<Medication> medications = new ArrayList<Medication>();

    @ManyToMany(fetch = FetchType.EAGER)
    protected List<Alarm> alarms = new ArrayList<Alarm>();

    @Override
    //workaround otherwise JSON conversion complaints
    //override roles and define attribute for conversion
    @JsonProperty(value = "patientRoles")
    public List<Role> getRoles() {
        return roles;
    }

    public String getMedicalRecordNumber() {
        return medicalRecordNumber;
    }

    public void setMedicalRecordNumber(String medicalRecordNumber) {
        this.medicalRecordNumber = medicalRecordNumber;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<CheckIn> getCheckIns() {
        return checkIns;
    }

    public void setCheckIns(List<CheckIn> checkIns) {
        this.checkIns = checkIns;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
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
                medicalRecordNumber,
                doctors,
                checkIns,
                medications);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Patient) {
            Patient other = (Patient) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(firstName, other.firstName)
                && Objects.equal(lastName, other.lastName)
                && Objects.equal(username, other.username)
                && Objects.equal(password, other.password)
                && Objects.equal(dateOfBirth, other.dateOfBirth)
                && Objects.equal(medicalRecordNumber, other.medicalRecordNumber)
                && Objects.equal(doctors, other.doctors)
                && Objects.equal(checkIns, other.checkIns)
                && Objects.equal(medications, other.medications);
        } else {
            return false;
        }
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }

}
