package org.aliensource.symptommanagement.cloud.repository.dto;

import com.google.gson.annotations.SerializedName;

import org.aliensource.symptommanagement.cloud.repository.IntakeTime;

import java.util.List;

/**
 * Created by ttruong on 23-Nov-14.
 */
public class IntakeTimeDTO extends BaseDTO<IntakeTime> {

    @SerializedName("intaketimes")
    private List<IntakeTime> models;

    @Override
    public List<IntakeTime> getModels() {
        return models;
    }

    @Override
    public void setModels(List<IntakeTime> models) {
        this.models = models;
    }
}
