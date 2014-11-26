package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.Doctor;
import org.aliensource.symptommanagement.cloud.repository.Role;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface RoleSvcApi {

	public static final String SVC_PATH = "/role";
    public static final String SVC_PATH_ID = SVC_PATH + "/{" + ServiceUtils.PARAMETER_ID + "}";

    @GET(SVC_PATH)
    public List<Role> findAll();

    @POST(SVC_PATH)
    public Role save(@Body Role checkIn);

    @GET(SVC_PATH_ID)
    public Doctor findOne(@Path(ServiceUtils.PARAMETER_ID) long id);

}
