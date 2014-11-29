package org.aliensource.symptommanagement.cloud.repository;

import com.google.common.base.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class IntakeTime extends BaseModel implements BaseTimestampModel {

    protected long timestamp;

    @ManyToOne
    protected Medicament medicament;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    @Override
    public int hashCode() {
        // Google Guava provides great utilities for hashing
        return Objects.hashCode(
                timestamp,
                medicament);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IntakeTime) {
            IntakeTime other = (IntakeTime) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(timestamp, other.timestamp)
                    && Objects.equal(medicament, other.medicament);
        } else {
            return false;
        }
    }

}
