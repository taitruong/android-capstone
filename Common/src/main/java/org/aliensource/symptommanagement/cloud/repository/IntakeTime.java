package org.aliensource.symptommanagement.cloud.repository;

import com.google.common.base.Objects;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class IntakeTime extends BaseModel {

    protected Calendar timestamp;

    @OneToOne(optional = false)
    protected Medication medication;

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    @Override
    public int hashCode() {
        // Google Guava provides great utilities for hashing
        return Objects.hashCode(
                timestamp,
                medication);
    }

    /**
     * Two Videos are considered equal if they have exactly the same values for
     * their name, url, and duration.
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IntakeTime) {
            IntakeTime other = (IntakeTime) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(timestamp, other.timestamp)
                    && Objects.equal(medication, other.medication);
        } else {
            return false;
        }
    }

}
