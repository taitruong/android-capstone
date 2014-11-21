/* 
 **
 ** Copyright 2014, Jules White
 **
 ** 
 */
package org.aliensource.symptommanagement.android;

import org.aliensource.symptommanagement.client.oauth.SecuredRestBuilder;
import org.aliensource.symptommanagement.client.EasyHttpClient;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.SecurityService;

import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class PatientSvc {

    public static final String CLIENT_ID = "mobile";

	private static PatientSvcApi patientService;

	public static synchronized PatientSvcApi getOrShowLogin(Context ctx) {
		if (patientService != null) {
			return patientService;
		} else {
			Intent i = new Intent(ctx, LoginScreenActivity.class);
			ctx.startActivity(i);
			return null;
		}
	}

    public static synchronized PatientSvcApi init(Activity activity) {
        String[] credentials = MainUtils.getCredentials(activity);
        return init(credentials[0], credentials[1], credentials[2]);
    }

    public static synchronized PatientSvcApi init(String server, String user,
			String pass) {

        patientService = new SecuredRestBuilder()
                .setLoginEndpoint(server + SecurityService.TOKEN_PATH)
                .setUsername(user)
                .setPassword(pass)
                .setClientId(CLIENT_ID)
                .setClient(new ApacheClient(new EasyHttpClient()))
                .setEndpoint(server)
                .setLogLevel(LogLevel.FULL).build()
                .create(PatientSvcApi.class);

		return patientService;
	}
}
