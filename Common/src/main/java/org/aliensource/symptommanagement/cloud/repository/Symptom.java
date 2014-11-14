package org.aliensource.symptommanagement.cloud.repository;

import java.util.Calendar;

import javax.persistence.Entity;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class Symptom extends BaseModel {

    private String name;

    private String severity;

    private Calendar timestamp;
}
