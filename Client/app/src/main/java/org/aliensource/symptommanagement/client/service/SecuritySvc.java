/* 
 **
 ** Copyright 2014, Jules White
 **
 ** 
 */
package org.aliensource.symptommanagement.client.service;

import org.aliensource.symptommanagement.cloud.service.SecurityService;

public class SecuritySvc extends BaseSvc<SecurityService> {

    private static final SecuritySvc service = new SecuritySvc();

    public static SecuritySvc getInstance() {
        return service;
    }

    @Override
    public Class<SecurityService> getApiClass() {
        return SecurityService.class;
    }


}
