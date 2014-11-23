package org.aliensource.symptommanagement.cloud.repository.dto;

import com.google.gson.annotations.SerializedName;

import org.aliensource.symptommanagement.cloud.repository.Patient;

import java.util.List;

/**
 * Created by ttruong on 23-Nov-14.
 */
public class PatientDTO extends BaseDTO<Patient> {

    @SerializedName("patients")
    private List<Patient> models;

    @Override
    public List<Patient> getModels() {
        return models;
    }

    @Override
    public void setModels(List<Patient> models) {
        this.models = models;
    }
}
