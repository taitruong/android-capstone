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

    public static final String ROLE_DOCTOR = "ROLE_DOCTOR";
    public static final String ROLE_PATIENT = "ROLE_PATIENT";

    @POST(ServiceUtils.PATH_AUTH_SERVICE)
    public boolean hasRole(@Query(ServiceUtils.PARAMETER_ROLE) String role);
}
