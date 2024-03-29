package org.aliensource.symptommanagement.cloud.repository;

import com.google.common.base.Objects;

import javax.persistence.Entity;

/**
 * Created by ttruong on 14-Nov-14.
 */
@Entity
public class Role extends BaseModel {

    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        // Google Guava provides great utilities for hashing
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Role) {
            Role other = (Role) obj;
            // Google Guava provides great utilities for equals too!
            return Objects.equal(name, other.name);
        } else {
            return false;
        }
    }
}