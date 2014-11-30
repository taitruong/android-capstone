/* 
 **
 ** Copyright 2014, Jules White
 **
 ** 
 */
package org.aliensource.symptommanagement.client.service;

import org.aliensource.symptommanagement.cloud.service.CheckInSvcApi;

public class CheckInSvc extends BaseSvc<CheckInSvcApi> {

    private static final CheckInSvc service = new CheckInSvc();

    public static CheckInSvc getInstance() {
        return service;
    }

    @Override
    public Class<CheckInSvcApi> getApiClass() {
        return CheckInSvcApi.class;
    }

}
