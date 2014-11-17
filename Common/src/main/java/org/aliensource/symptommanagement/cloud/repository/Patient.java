package org.aliensource.symptommanagement.cloud.repository;

import java.util.Collection;

import javax.persistence.Entity;
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
    private Collection<Doctor> doctors;

    @OneToOne
    private CheckIn checkIn;

    @OneToMany
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

}
