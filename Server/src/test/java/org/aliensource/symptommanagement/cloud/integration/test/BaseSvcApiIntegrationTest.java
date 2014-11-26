package org.aliensource.symptommanagement.cloud.integration.test;

import org.aliensource.symptommanagement.client.EasyHttpClient;
import org.aliensource.symptommanagement.client.oauth.SecuredRestBuilder;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.SecurityService;

import java.util.UUID;

import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;

public abstract class BaseSvcApiIntegrationTest<API> {

    protected static final String USERNAME = "patient1";
    protected static final String PASSWORD = "pass";
    protected static final String CLIENT_ID = "mobile";
    protected static final String READ_ONLY_CLIENT_ID = "mobileReader";

    protected static final String TEST_URL = "https://localhost:8443";

    public static <T> T getService(Class<T> apiClass) {
        T service = new SecuredRestBuilder()
                .setLoginEndpoint(TEST_URL + SecurityService.TOKEN_PATH)
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .setClientId(CLIENT_ID)
                .setClient(new ApacheClient(new EasyHttpClient()))
                .setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
                .create(apiClass);
        return service;
    }

    protected API service = getService(getApiClass());

    public static <T> T getReadOnlyService(Class<T> apiClass) {
        return new SecuredRestBuilder()
                .setLoginEndpoint(TEST_URL + SecurityService.TOKEN_PATH)
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .setClientId(READ_ONLY_CLIENT_ID)
                .setClient(new ApacheClient(new EasyHttpClient()))
                .setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
                .create(apiClass);
    }

    protected API readOnlyService = getReadOnlyService(getApiClass());

    protected API invalidClientService = new SecuredRestBuilder()
            .setLoginEndpoint(TEST_URL + SecurityService.TOKEN_PATH)
            .setUsername(UUID.randomUUID().toString())
            .setPassword(UUID.randomUUID().toString())
            .setClientId(UUID.randomUUID().toString())
            .setClient(new ApacheClient(new EasyHttpClient()))
            .setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
            .create(getApiClass());

    public abstract Class<API> getApiClass();

}
