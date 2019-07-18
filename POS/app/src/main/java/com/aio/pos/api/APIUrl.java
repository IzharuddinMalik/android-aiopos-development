package com.aio.pos.api;

public class APIUrl {
    // ini adalah IP localhost.
    public static final String BASE_URL_API = "http://backoffice.aiopos.id/ci_aiopos/api/";

    public static BaseApiInterface getAPIService(){
        return APIClient.getClient(BASE_URL_API).create(BaseApiInterface.class);
    }
}
