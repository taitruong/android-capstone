package org.aliensource.symptommanagement.cloud.repository.dto;

import com.google.gson.annotations.SerializedName;

import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.Role;

import java.util.List;

/**
 * Created by ttruong on 23-Nov-14.
 */
public class CheckInDTO extends BaseDTO<CheckIn> {

    @SerializedName("checkins")
    private List<CheckIn> models;

    @Override
    public List<CheckIn> getModels() {
        return models;
    }

    @Override
    public void setModels(List<CheckIn> models) {
        this.models = models;
    }
}
