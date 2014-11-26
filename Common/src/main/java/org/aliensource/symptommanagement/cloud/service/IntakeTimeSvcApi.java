package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.IntakeTime;

import java.util.Collection;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface IntakeTimeSvcApi {

	public static final String SVC_PATH = "/intaketime";
    public static final String SVC_PATH_ID = SVC_PATH + "/{" + ServiceUtils.PARAMETER_ID + "}";

    @GET(SVC_PATH)
    public List<IntakeTime> findAll();

    @POST(SVC_PATH)
    public IntakeTime save(@Body IntakeTime intakeTime);

    @POST(SVC_PATH)
    public List<IntakeTime> save(@Body Collection<IntakeTime> symptomTimes);

    @GET(SVC_PATH_ID)
    public IntakeTime findOne(@Path(ServiceUtils.PARAMETER_ID) long id);
}
