package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.Reminder;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface ReminderSvcApi {

	public static final String SVC_PATH = "/reminder";
    public static final String SVC_PATH_ID = SVC_PATH + "/{" + ServiceUtils.PARAMETER_ID + "}";
    public static final String SEARCH_PATH_PATIENT = SVC_PATH + "/search/findByPatientId" + "/{" + ServiceUtils.PARAMETER_ID + "}";

    @GET(SVC_PATH)
    public List<Reminder> findAll();

    @POST(SVC_PATH)
    public Reminder save(@Body Reminder checkIn);

    @GET(SVC_PATH_ID)
    public Reminder findOne(@Path(ServiceUtils.PARAMETER_ID) long id);

    @GET(SEARCH_PATH_PATIENT)
    public List<Reminder> findByPatientId(@Path(ServiceUtils.PARAMETER_ID) long patientId);
}
