package org.aliensource.symptommanagement.cloud.repository;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Created by ttruong on 13-Nov-14.
 */
@Entity
public class Patient extends Person {

    private String medicalRecordNumber;

    @ManyToMany
    //define join table with the Patient entity being the owner
    @JoinTable(
            name = "PATIENT_DOCTOR",
            joinColumns = @JoinColumn(name="patient_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private Collection<Doctor> doctors = new ArrayList<Doctor>();

    @OneToOne
    private CheckIn checkIn;

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Medication> medications = new ArrayList<Medication>();

    public String getMedicalRecordNumber() {
        return medicalRecordNumber;
    }

    public void setMedicalRecordNumber(String medicalRecordNumber) {
        this.medicalRecordNumber = medicalRecordNumber;
    }

    public Collection<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Collection<Doctor> doctors) {
        this.doctors = doctors;
    }

    public CheckIn getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(CheckIn checkIn) {
        this.checkIn = checkIn;
    }

    public Collection<Medication> getMedications() {
        return medications;
    }

    public void setMedications(Collection<Medication> medications) {
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
                checkIn,
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
                && Objects.equal(checkIn, other.checkIn)
                && Objects.equal(medications, other.medications);
        } else {
            return false;
        }
    }
}
