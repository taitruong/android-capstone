package org.aliensource.symptommanagement.cloud.repository;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Calendar;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class SymptomTime extends BaseModel {

    private Calendar timestamp;

    private String severity;

    @OneToOne(optional = false)
    private Symptom symptom;

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Symptom getSymptom() {
        return symptom;
    }

    public void setSymptom(Symptom symptom) {
        this.symptom = symptom;
    }
}
