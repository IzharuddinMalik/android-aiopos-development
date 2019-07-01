package com.ultra.pos.api;

import com.ultra.pos.model.KategoriModel;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface BaseApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> postLogin(@FieldMap HashMap<String, String> params, @Field("email_user") String emailUser,
                                 @Field("password_user") String passwordUser);

    @FormUrlEncoded
    @POST("produk_get_kategori.php")
    Call<ResponseBody> getKategori(@FieldMap HashMap<String, String> params, @Field("idbusiness") String idBusiness);
}
