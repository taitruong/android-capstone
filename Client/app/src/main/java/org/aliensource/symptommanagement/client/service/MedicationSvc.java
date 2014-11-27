/* 
 **
 ** Copyright 2014, Jules White
 **
 ** 
 */
package org.aliensource.symptommanagement.client.service;

import org.aliensource.symptommanagement.cloud.service.MedicationSvcApi;

public class MedicationSvc extends BaseSvc<MedicationSvcApi> {

    private static final MedicationSvc service = new MedicationSvc();

    public static MedicationSvc getInstance() {
        return service;
    }

    @Override
    public Class<MedicationSvcApi> getApiClass() {
        return MedicationSvcApi.class;
    }

}
