package org.aliensource.symptommanagement.cloud.repository.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ttruong on 23-Nov-14.
 */
public abstract class BaseDTO<M> {

    public abstract List<M> getModels();

    public abstract void setModels(List<M> models);

}
