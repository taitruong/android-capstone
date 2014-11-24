/* 
 **
 ** Copyright 2014, Jules White
 **
 ** 
 */
package org.aliensource.symptommanagement.client.service;

import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;

public class PatientSvc extends BaseSvc<PatientSvcApi> {

    private static final PatientSvc service = new PatientSvc();

    public static PatientSvc getInstance() {
        return service;
    }

    @Override
    public Class<PatientSvcApi> getApiClass() {
        return PatientSvcApi.class;
    }

}
