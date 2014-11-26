package org.aliensource.symptommanagement.cloud.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class Medication extends BaseModel {

//    @RestResource(exported =  false, rel = "medicationMedicament")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonProperty(value = "medicationMedicament")
    protected Medicament medicament;

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    @Override
    public int hashCode() {
        // Google Guava provides great utilities for hashing
        return Objects.hashCode(medicament);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IntakeTime) {
            Medication other = (Medication) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(medicament, other.medicament);
        } else {
            return false;
        }
    }


}