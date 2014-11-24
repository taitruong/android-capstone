/* 
 **
 ** Copyright 2014, Jules White
 **
 ** 
 */
package org.aliensource.symptommanagement.client.service;

import org.aliensource.symptommanagement.cloud.service.SymptomSvcApi;

public class SymptomSvc extends BaseSvc<SymptomSvcApi> {

    private static final SymptomSvc service = new SymptomSvc();

    public static SymptomSvc getInstance() {
        return service;
    }

    @Override
    public Class<SymptomSvcApi> getApiClass() {
        return SymptomSvcApi.class;
    }

}
