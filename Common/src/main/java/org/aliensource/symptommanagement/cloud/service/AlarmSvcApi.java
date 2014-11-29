package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.Alarm;
import org.aliensource.symptommanagement.cloud.repository.Doctor;
import org.aliensource.symptommanagement.cloud.repository.Role;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface AlarmSvcApi {

	public static final String SVC_PATH = "/alarm";
    public static final String SVC_PATH_ID = SVC_PATH + "/{" + ServiceUtils.PARAMETER_ID + "}";

    @GET(SVC_PATH)
    public List<Alarm> findAll();

    @POST(SVC_PATH)
    public Alarm save(@Body Alarm checkIn);

    @GET(SVC_PATH_ID)
    public Alarm findOne(@Path(ServiceUtils.PARAMETER_ID) long id);

}