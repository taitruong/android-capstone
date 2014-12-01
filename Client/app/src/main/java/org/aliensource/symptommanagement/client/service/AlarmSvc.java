/* 
 **
 ** Copyright 2014, Jules White
 **
 ** 
 */
package org.aliensource.symptommanagement.client.service;

import org.aliensource.symptommanagement.cloud.service.AlarmSvcApi;

public class AlarmSvc extends BaseSvc<AlarmSvcApi> {

    private static final AlarmSvc service = new AlarmSvc();

    public static AlarmSvc getInstance() {
        return service;
    }

    @Override
    public Class<AlarmSvcApi> getApiClass() {
        return AlarmSvcApi.class;
    }

}
