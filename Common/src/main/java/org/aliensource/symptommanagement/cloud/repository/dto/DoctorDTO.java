package org.aliensource.symptommanagement.cloud.repository.dto;

import com.google.gson.annotations.SerializedName;

import org.aliensource.symptommanagement.cloud.repository.Doctor;

import java.util.List;

/**
 * Created by ttruong on 23-Nov-14.
 */
public class DoctorDTO extends BaseDTO<Doctor> {

    @SerializedName("doctors")
    private List<Doctor> models;

    @Override
    public List<Doctor> getModels() {
        return models;
    }

    @Override
    public void setModels(List<Doctor> models) {
        this.models = models;
    }
}
