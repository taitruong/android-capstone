package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.Medicament;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

public interface MedicamentSvcApi {

	public static final String SVC_PATH = "/medicament";
    public static final String SVC_PATH_ID = SVC_PATH + "/{" + ServiceUtils.PARAMETER_ID + "}";

    @GET(SVC_PATH)
    public List<Medicament> findAll();

    @GET(SVC_PATH_ID)
    public Medicament findOne(@Path(ServiceUtils.PARAMETER_ID) long id);

}
