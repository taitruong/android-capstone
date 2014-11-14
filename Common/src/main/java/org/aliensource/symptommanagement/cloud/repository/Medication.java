package org.aliensource.symptommanagement.cloud.repository;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class Medication extends BaseModel {

    @OneToOne
    private Medicament medicament;

    @OneToMany
    private Collection<IntakeTime> intakeTimes;

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

}
