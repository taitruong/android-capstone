package org.aliensource.symptommanagement.cloud.repository;

import java.util.Calendar;

import javax.persistence.Entity;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class Symptom extends BaseModel {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
