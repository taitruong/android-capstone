package org.aliensource.symptommanagement.cloud.repository;

import com.google.common.base.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class SymptomTime extends BaseModel implements BaseTimestampModel {

    protected long timestamp;

    protected int severity;

    //do not use ManyToOne otherwise Gson complains "Expected BEGIN_OBJECT but was NUMBER"
    // when calling e.g. CheckInController.findAll
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected Symptom symptom;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public Symptom getSymptom() {
        return symptom;
    }

    public void setSymptom(Symptom symptom) {
        this.symptom = symptom;
    }

    @Override
    public int hashCode() {
        // Google Guava provides great utilities for hashing
        return Objects.hashCode(
                timestamp,
                severity,
                symptom);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SymptomTime) {
            SymptomTime other = (SymptomTime) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(timestamp, other.timestamp)
                    && Objects.equal(severity, other.severity)
                    && Objects.equal(symptom, other.symptom);
        } else {
            return false;
        }
    }
}
