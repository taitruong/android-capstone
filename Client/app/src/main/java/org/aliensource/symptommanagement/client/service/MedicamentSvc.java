/* 
 **
 ** Copyright 2014, Jules White
 **
 ** 
 */
package org.aliensource.symptommanagement.client.service;

import org.aliensource.symptommanagement.cloud.service.MedicamentSvcApi;
import org.aliensource.symptommanagement.cloud.service.MedicationSvcApi;

public class MedicamentSvc extends BaseSvc<MedicamentSvcApi> {

    private static final MedicamentSvc service = new MedicamentSvc();

    public static MedicamentSvc getInstance() {
        return service;
    }

    @Override
    public Class<MedicamentSvcApi> getApiClass() {
        return MedicamentSvcApi.class;
    }

}
