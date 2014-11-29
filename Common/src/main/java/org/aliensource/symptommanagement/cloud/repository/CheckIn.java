package org.aliensource.symptommanagement.cloud.repository;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class CheckIn extends BaseModel {

    protected long timestamp;

    /**
     * WORKAROUND (otherwise we get a StackOverflowError
     * This workaround is needed for PatientSvcApiIntegrationTest.testCheckIn().
     * Otherwise when a new check-in is created and attached to the patient we get two references
     * of the same check-in in the Patient.checkins-list.
     */
    @ManyToOne
    protected Patient patient;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.DETACH, CascadeType.REFRESH})
    protected List<SymptomTime> symptomTimes = new ArrayList<SymptomTime>();

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.DETACH, CascadeType.REFRESH})
    protected List<IntakeTime> intakeTimes = new ArrayList<IntakeTime>();

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<SymptomTime> getSymptomTimes() {
        return symptomTimes;
    }

    public void setSymptomTimes(List<SymptomTime> symptomTimes) {
        this.symptomTimes = symptomTimes;
    }

    public List<IntakeTime> getIntakeTimes() {
        return intakeTimes;
    }

    public void setIntakeTimes(List<IntakeTime> intakeTimes) {
        this.intakeTimes = intakeTimes;
    }

    @Override
    public int hashCode() {
        // Google Guava provides great utilities for hashing
        return Objects.hashCode(
                symptomTimes,
                intakeTimes);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CheckIn) {
            CheckIn other = (CheckIn) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(symptomTimes, other.symptomTimes)
                    && Objects.equal(intakeTimes, other.intakeTimes);
        } else {
            return false;
        }
    }
}
