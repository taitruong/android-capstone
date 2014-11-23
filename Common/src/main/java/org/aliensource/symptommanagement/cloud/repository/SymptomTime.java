package org.aliensource.symptommanagement.cloud.repository;

import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Calendar;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class SymptomTime extends BaseModel {

    private Calendar timestamp;

    private int severity;

    @OneToOne(optional = false)
    private Symptom symptom;

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
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

    /**
     * Two Videos are considered equal if they have exactly the same values for
     * their name, url, and duration.
     *
     */
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
