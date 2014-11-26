package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.Symptom;
import org.aliensource.symptommanagement.cloud.repository.SymptomTime;

import java.util.Collection;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface SymptomTimeSvcApi {

	public static final String SVC_PATH = "/symptomTime";
    public static final String SVC_PATH_ID = SVC_PATH + "/{" + ServiceUtils.PARAMETER_ID + "}";

    @GET(SVC_PATH)
    public List<SymptomTime> findAll();

    @POST(SVC_PATH)
    public SymptomTime save(@Body SymptomTime symptomTime);

    @GET(SVC_PATH_ID)
    public SymptomTime findOne(@Path(ServiceUtils.PARAMETER_ID) long id);

}
