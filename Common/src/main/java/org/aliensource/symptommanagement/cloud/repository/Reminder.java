package org.aliensource.symptommanagement.cloud.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by ttruong on 16-Dec-14.
 */
@Entity
public class Reminder extends BaseModel implements BaseTimestampModel{

    @ManyToOne
    @JoinColumn(name = "patient_id")
    //needs to be ignored, otherwise we get an infinite cycle in the JSON output
    @JsonIgnore
    protected Patient patient;

    protected long timestamp;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        // Google Guava provides great utilities for hashing
        return Objects.hashCode(
                patient,
                timestamp);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Reminder) {
            Reminder other = (Reminder) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(patient, other.patient)
                    && Objects.equal(timestamp, other.timestamp);
        } else {
            return false;
        }
    }
}
