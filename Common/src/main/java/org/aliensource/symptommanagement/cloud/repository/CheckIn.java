package org.aliensource.symptommanagement.cloud.repository;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class CheckIn extends BaseModel {

    @ManyToMany
    //define join table with the Patient entity being the owner
    @JoinTable(
            name = "CHECKIN_SYMPTOM",
            joinColumns = @JoinColumn(name="checkin_id", referencedColumnName = "Id"),
            inverseJoinColumns = @JoinColumn(name = "symptom_id", referencedColumnName = "Id")
    )
    private Collection<Symptom> symptoms;

    @ManyToMany
    //define join table with the Patient entity being the owner
    @JoinTable(
            name = "CHECKIN_INTAKETIME",
            joinColumns = @JoinColumn(name="checkin_id", referencedColumnName = "Id"),
            inverseJoinColumns = @JoinColumn(name = "intaketime_id", referencedColumnName = "Id")
    )
    private Collection<IntakeTime> intakeTimes;

    public Collection<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Collection<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public Collection<IntakeTime> getIntakeTimes() {
        return intakeTimes;
    }

    public void setIntakeTimes(Collection<IntakeTime> intakeTimes) {
        this.intakeTimes = intakeTimes;
    }
}
