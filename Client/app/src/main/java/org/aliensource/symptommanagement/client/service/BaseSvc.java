/* 
 **
 ** Copyright 2014, Jules White
 **
 ** 
 */
package org.aliensource.symptommanagement.client.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.aliensource.symptommanagement.android.LoginScreenActivity;
import org.aliensource.symptommanagement.android.main.CredentialsWrapper;
import org.aliensource.symptommanagement.android.main.MainUtils;
import org.aliensource.symptommanagement.client.EasyHttpClient;
import org.aliensource.symptommanagement.client.oauth.SecuredRestBuilder;
import org.aliensource.symptommanagement.cloud.service.SecurityService;

import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;

public abstract class BaseSvc<API> {

    public static final String CLIENT_ID = "mobile";

	protected API service;

    public abstract Class<API> getApiClass();

	public synchronized API getOrShowLogin(Context ctx) {
		if (service != null) {
			return service;
		} else {
			Intent i = new Intent(ctx, LoginScreenActivity.class);
			ctx.startActivity(i);
			return null;
		}
	}

    public synchronized API init(Activity activity) {
        CredentialsWrapper data = MainUtils.getCredentials(activity);
        return init(data.server, data.username, data.password);
    }

    public synchronized API init(String server, String user,
			String pass) {

        service = new SecuredRestBuilder()
                .setLoginEndpoint(server + SecurityService.TOKEN_PATH)
                .setUsername(user)
                .setPassword(pass)
                .setClientId(CLIENT_ID)
                .setClient(new ApacheClient(new EasyHttpClient()))
                .setEndpoint(server)
                .setLogLevel(LogLevel.FULL).build()
                .create(getApiClass());

		return service;
	}
}
