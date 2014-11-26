package org.aliensource.symptommanagement.cloud.repository.dto;

import com.google.gson.annotations.SerializedName;

import org.aliensource.symptommanagement.cloud.repository.Medicament;

import java.util.List;

/**
 * Created by ttruong on 23-Nov-14.
 */
public class MedicamentDTO extends BaseDTO<Medicament> {

    @SerializedName("medicaments")
    private List<Medicament> models;

    @Override
    public List<Medicament> getModels() {
        return models;
    }

    @Override
    public void setModels(List<Medicament> models) {
        this.models = models;
    }
}
