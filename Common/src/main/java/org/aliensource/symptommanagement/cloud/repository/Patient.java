package org.aliensource.symptommanagement.cloud.repository;

import com.google.common.base.Objects;

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
public class
        Patient extends Person {

    private String medicalRecordNumber;

    @ManyToMany
    //define join table with the Patient entity being the owner
    @JoinTable(
            name = "PATIENT_DOCTOR",
            joinColumns = @JoinColumn(name="patient_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private Collection<Doctor> doctors;

    @OneToOne
    private CheckIn checkIn;

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Medication> medications;

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
        return Objects.hashCode(medicalRecordNumber, doctors, checkIn, medications);
    }

    /**
     * Two Videos are considered equal if they have exactly the same values for
     * their name, url, and duration.
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Patient) {
            Patient other = (Patient) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(medicalRecordNumber, other.medicalRecordNumber)
                    && Objects.equal(doctors, other.doctors)
                    && Objects.equal(checkIn, other.checkIn)
                    && Objects.equal(medications, other.medications);
        } else {
            return false;
        }
    }
}
