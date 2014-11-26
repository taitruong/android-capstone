package org.aliensource.symptommanagement.cloud.repository.dto;

import com.google.gson.annotations.SerializedName;

import org.aliensource.symptommanagement.cloud.repository.Medication;
import org.aliensource.symptommanagement.cloud.repository.SymptomTime;

import java.util.List;

/**
 * Created by ttruong on 23-Nov-14.
 */
public class SymptomTimeDTO extends BaseDTO<SymptomTime> {

    @SerializedName("medications")
    private List<SymptomTime> models;

    @Override
    public List<SymptomTime> getModels() {
        return models;
    }

    @Override
    public void setModels(List<SymptomTime> models) {
        this.models = models;
    }
}
