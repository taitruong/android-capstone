package org.aliensource.symptommanagement.cloud.repository.dto;

import com.google.gson.annotations.SerializedName;

import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.Role;

import java.util.List;

/**
 * Created by ttruong on 23-Nov-14.
 */
public class RoleDTO extends BaseDTO<Role> {

    @SerializedName("roles")
    private List<Role> models;

    @Override
    public List<Role> getModels() {
        return models;
    }

    @Override
    public void setModels(List<Role> models) {
        this.models = models;
    }
}
