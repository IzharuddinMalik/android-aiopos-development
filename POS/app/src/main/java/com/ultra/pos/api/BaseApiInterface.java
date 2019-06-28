package com.ultra.pos.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> postLogin(@Field("email_user") String emailUser,
                                 @Field("password_user") String passwordUser);

}
