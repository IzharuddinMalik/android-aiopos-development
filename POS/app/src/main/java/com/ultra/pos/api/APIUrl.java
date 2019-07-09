package com.ultra.pos.api;

public class APIUrl {
    // ini adalah IP localhost.
    public static final String BASE_URL_API = "http://backoffice.aiopos.id/";

    public static BaseApiInterface getAPIService(){
        return APIClient.getClient(BASE_URL_API).create(BaseApiInterface.class);
    }
}
