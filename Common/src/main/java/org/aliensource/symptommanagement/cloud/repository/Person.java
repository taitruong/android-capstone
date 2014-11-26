package org.aliensource.symptommanagement.cloud.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

/**
 * Created by ttruong on 13-Nov-14.
 */
@MappedSuperclass
//ignore role attribute since the sub classes overrides the get method and tells JSON another attribute name than "roles"
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Person extends BaseModel {

    protected String firstName;

    protected String lastName;

    protected String username;

    protected String password;

    //cannot be a Calendar otherwise Retrofit cannot convert a String into an Object (Calendar)
    protected long dateOfBirth;

    @ManyToMany(fetch = FetchType.EAGER)
    protected List<Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return getId() + ", " + firstName + ", " + lastName + ", " + username;
    }
}
