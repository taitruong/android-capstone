package org.aliensource.symptommanagement.cloud.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by ttruong on 29-Nov-14.
 */
@Entity
public class Alarm extends BaseModel {

    protected long patientId;
    protected long start;
    protected long end;

    protected int severity;

    @ManyToOne(fetch = FetchType.EAGER)
    protected Symptom symptom;

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
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
                patientId,
                start,
                end,
                symptom);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Alarm) {
            Alarm other = (Alarm) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(patientId, other.patientId)
                    && Objects.equal(start, other.start)
                    && Objects.equal(end, other.end)
                    && Objects.equal(symptom, other.symptom);
        } else {
            return false;
        }
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

}
