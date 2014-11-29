package org.aliensource.symptommanagement.cloud.repository;

import com.google.common.base.Objects;

import javax.persistence.Entity;

/**
 * Created by ttruong on 29-Nov-14.
 */
@Entity
public class Alarm extends BaseModel {

    protected long start;
    protected long end;
    protected Symptom symptom;

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
                start,
                end,
                symptom);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Alarm) {
            Alarm other = (Alarm) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(start, other.start)
                    && Objects.equal(end, other.end)
                    && Objects.equal(symptom, other.symptom);
        } else {
            return false;
        }
    }

}
