package org.aliensource.symptommanagement.cloud.service;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by ttruong on 05-Nov-14.
 */
public interface SecurityService {

    public static final String TOKEN_PATH = "/oauth/token";

    public static final String ROLE_DOCTOR = "Doctor";
    public static final String ROLE_PATIENT = "Patient";

    @POST(ServiceUtils.PATH_AUTH_SERVICE)
    public boolean hasRole(@Query(ServiceUtils.PARAMETER_ROLE) String role);
}
