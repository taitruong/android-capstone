package org.aliensource.symptommanagement.cloud.repository;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class CheckIn extends BaseModel {

    @OneToMany(fetch = FetchType.EAGER)
    protected Collection<SymptomTime> symptomTimes = new ArrayList<SymptomTime>();

    @ManyToMany(fetch = FetchType.EAGER)
    //define join table with the Patient entity being the owner
    @JoinTable(
            name = "CHECKIN_INTAKETIME",
            joinColumns = @JoinColumn(name="checkin_id"),
            inverseJoinColumns = @JoinColumn(name = "intaketime_id")
    )
    protected Collection<IntakeTime> intakeTimes = new ArrayList<IntakeTime>();

    public Collection<SymptomTime> getSymptomTimes() {
        return symptomTimes;
    }

    public void setSymptomTimes(Collection<SymptomTime> symptomTimes) {
        this.symptomTimes = symptomTimes;
    }

    public Collection<IntakeTime> getIntakeTimes() {
        return intakeTimes;
    }

    public void setIntakeTimes(Collection<IntakeTime> intakeTimes) {
        this.intakeTimes = intakeTimes;
    }

    @Override
    public int hashCode() {
        // Google Guava provides great utilities for hashing
        return Objects.hashCode(
                symptomTimes,
                intakeTimes);
    }

    /**
     * Two Videos are considered equal if they have exactly the same values for
     * their name, url, and duration.
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CheckIn) {
            CheckIn other = (CheckIn) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(symptomTimes, other.symptomTimes)
                    && Objects.equal(intakeTimes, other.intakeTimes);
        } else {
            return false;
        }
    }
}
