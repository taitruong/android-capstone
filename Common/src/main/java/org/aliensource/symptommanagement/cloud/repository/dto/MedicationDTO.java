package org.aliensource.symptommanagement.cloud.repository.dto;

import com.google.gson.annotations.SerializedName;

import org.aliensource.symptommanagement.cloud.repository.IntakeTime;
import org.aliensource.symptommanagement.cloud.repository.Medication;

import java.util.List;

/**
 * Created by ttruong on 23-Nov-14.
 */
public class MedicationDTO extends BaseDTO<Medication> {

    @SerializedName("medications")
    private List<Medication> models;

    @Override
    public List<Medication> getModels() {
        return models;
    }

    @Override
    public void setModels(List<Medication> models) {
        this.models = models;
    }
}
