package com.ultra.pos.api;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BaseApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> postLogin(@FieldMap HashMap<String, String> params, @Field("email_user") String emailUser,
                                 @Field("password_user") String passwordUser);

    @FormUrlEncoded
    @POST("pelanggan_get_list.php")
    Call<ResponseBody> listPelanggan(@FieldMap HashMap<String, String> params, @Field("idbusiness") String idBusiness,
                                     @Field("idoutlet") String idOutlet);

    @FormUrlEncoded
    @POST("pelanggan_get_detail.php")
    Call<ResponseBody> detailPelanggan(@FieldMap HashMap<String, String> params, @Field("idctm") String idPelanggan);

    @FormUrlEncoded
    @POST("pelanggan_tambah.php")
    Call<ResponseBody> insertPelanggan(@FieldMap HashMap<String, String> params, @Field("idbusiness") String idBusiness,
                                       @Field("idoutlet") String idOutlet, @Field("province_id") String province_id,
                                       @Field("regencies_id") String regencies_id, @Field("district_id") String district_id,
                                       @Field("nama_pelanggan") String nama_pelanggan, @Field("idoutlet") String email_pelanggan,
                                       @Field("telp_pelanggan") String telp_pelanggan, @Field("telepon_pelanggan2") String telepon_pelanggan2);

    @GET("wilayah_get_provinsi.php")
    Call<ResponseBody> getProvinsi();

    @FormUrlEncoded
    @POST("wilayah_get_kab_kec.php")
    Call<ResponseBody> getKecamatan(@FieldMap HashMap<String, String> params,
                                    @Field("id") String id, @Field("pilih") String pilih);

    @FormUrlEncoded
    @POST("get_produk.php")
    Call<ResponseBody> getProduk(@FieldMap HashMap<String, String> params,
                                 @Field("idbusiness") String idBusiness, @Field("idtb") String idTB, @Field("idoutlet") String idOutlet);
}
