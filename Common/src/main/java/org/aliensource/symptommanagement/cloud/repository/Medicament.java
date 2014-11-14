package org.aliensource.symptommanagement.cloud.repository;

import javax.persistence.Entity;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class Medicament extends BaseModel {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
