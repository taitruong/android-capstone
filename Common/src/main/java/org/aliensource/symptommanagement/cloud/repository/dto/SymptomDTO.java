package org.aliensource.symptommanagement.cloud.repository.dto;

import com.google.gson.annotations.SerializedName;

import org.aliensource.symptommanagement.cloud.repository.Role;
import org.aliensource.symptommanagement.cloud.repository.Symptom;

import java.util.List;

/**
 * Created by ttruong on 23-Nov-14.
 */
public class SymptomDTO extends BaseDTO<Symptom> {

    @SerializedName("symptoms")
    private List<Symptom> models;

    @Override
    public List<Symptom> getModels() {
        return models;
    }

    @Override
    public void setModels(List<Symptom> models) {
        this.models = models;
    }
}
