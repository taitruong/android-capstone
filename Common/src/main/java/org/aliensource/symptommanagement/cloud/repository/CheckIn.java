package org.aliensource.symptommanagement.cloud.repository;

import java.util.Collection;

import javax.persistence.*;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class CheckIn extends BaseModel {

    @OneToMany
    private Collection<Symptom> symptomTimes;

    @ManyToMany
    //define join table with the Patient entity being the owner
    @JoinTable(
            name = "CHECKIN_INTAKETIME",
            joinColumns = @JoinColumn(name="checkin_id"),
            inverseJoinColumns = @JoinColumn(name = "intaketime_id")
    )
    private Collection<IntakeTime> intakeTimes;

    public Collection<Symptom> getSymptomTimes() {
        return symptomTimes;
    }

    public void setSymptomTimes(Collection<Symptom> symptomTimes) {
        this.symptomTimes = symptomTimes;
    }

    public Collection<IntakeTime> getIntakeTimes() {
        return intakeTimes;
    }

    public void setIntakeTimes(Collection<IntakeTime> intakeTimes) {
        this.intakeTimes = intakeTimes;
    }
}
