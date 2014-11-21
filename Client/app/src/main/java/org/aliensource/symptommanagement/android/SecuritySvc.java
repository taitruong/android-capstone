/* 
 **
 ** Copyright 2014, Jules White
 **
 ** 
 */
package org.aliensource.symptommanagement.android;

import android.content.Context;
import android.content.Intent;

import org.aliensource.symptommanagement.client.EasyHttpClient;
import org.aliensource.symptommanagement.client.oauth.SecuredRestBuilder;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.SecurityService;

import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;

public class SecuritySvc {

    public static final String CLIENT_ID = "mobile";

    private static SecurityService securityService;

    public static synchronized SecurityService getSecurityServiceOrShowLogin(Context ctx) {
        if (securityService != null) {
            return securityService;
        } else {
            Intent i = new Intent(ctx, LoginScreenActivity.class);
            ctx.startActivity(i);
            return null;
        }
    }

    public static synchronized SecurityService init(String server, String user,
			String pass) {

        securityService = new SecuredRestBuilder()
                .setLoginEndpoint(server + PatientSvcApi.TOKEN_PATH)
                .setUsername(user)
                .setPassword(pass)
                .setClientId(CLIENT_ID)
                .setClient(
                        new ApacheClient(new EasyHttpClient()))
                .setEndpoint(server).setLogLevel(LogLevel.FULL).build()
                .create(SecurityService.class);

		return securityService;
	}
}
