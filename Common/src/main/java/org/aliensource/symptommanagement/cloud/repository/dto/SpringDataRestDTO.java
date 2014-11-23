package org.aliensource.symptommanagement.cloud.repository.dto;

import com.google.gson.annotations.SerializedName;

import org.aliensource.symptommanagement.cloud.repository.BaseModel;
import org.aliensource.symptommanagement.cloud.repository.Patient;

import java.util.List;

/**
 * Created by ttruong on 23-Nov-14.
 */
public class SpringDataRestDTO<M extends BaseDTO> {

    @SerializedName("_embedded")
    private M embedded;

    public SpringDataRestDTO() {
    }

    public SpringDataRestDTO(M e) {
        this.embedded = e;
    }

    public void setEmbedded(M embedded) {
        this.embedded = embedded;
    }

    public M getEmbedded() {
        return embedded;
    }

}
