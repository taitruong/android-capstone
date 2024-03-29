package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.Medication;

import java.util.Collection;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface MedicationSvcApi {

	public static final String SVC_PATH = "/medication";
    public static final String SVC_PATH_ID = SVC_PATH + "/{" + ServiceUtils.PARAMETER_ID + "}";

    @GET(SVC_PATH)
    public List<Medication> findAll();

    @GET(SVC_PATH_ID)
    public Medication findOne(@Path(ServiceUtils.PARAMETER_ID) long id);

    @POST(SVC_PATH)
    public Medication save(@Body Medication Medication);

    @DELETE(SVC_PATH_ID)
    public Void delete(@Path(ServiceUtils.PARAMETER_ID) long id);
}
