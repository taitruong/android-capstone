package org.aliensource.symptommanagement.cloud.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class CheckIn extends BaseModel {

    protected long timestamp;

    protected long patientId;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    protected List<SymptomTime> symptomTimes = new ArrayList<SymptomTime>();

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    protected List<IntakeTime> intakeTimes = new ArrayList<IntakeTime>();

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
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
                timestamp,
                patientId,
                symptomTimes,
                intakeTimes);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CheckIn) {
            CheckIn other = (CheckIn) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(timestamp, other.timestamp)
                    && Objects.equal(patientId, other.patientId)
                    && Objects.equal(symptomTimes, other.symptomTimes)
                    && Objects.equal(intakeTimes, other.intakeTimes);
        } else {
            return false;
        }
    }
}
