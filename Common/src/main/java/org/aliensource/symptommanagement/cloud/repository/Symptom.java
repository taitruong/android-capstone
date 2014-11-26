package org.aliensource.symptommanagement.cloud.repository;

import com.google.common.base.Objects;

import javax.persistence.Entity;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class Symptom extends BaseModel {

    protected String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        // Google Guava provides great utilities for hashing
        return Objects.hashCode(type);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Role) {
            Symptom other = (Symptom) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(type, other.type);
        } else {
            return false;
        }
    }
}
