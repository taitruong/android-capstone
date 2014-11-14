package org.aliensource.symptommanagement.cloud.repository;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class IntakeTime extends BaseModel {

    private Calendar timestamp;

    @OneToOne(optional = false)
    private Medication medication;

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
}
