package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.dto.RoleDTO;
import org.aliensource.symptommanagement.cloud.repository.dto.SpringDataRestDTO;

import retrofit.http.GET;

public interface RoleSvcApi {

	public static final String SVC_PATH = "/role";

    @GET(SVC_PATH)
    public SpringDataRestDTO<RoleDTO> findAll();
}
