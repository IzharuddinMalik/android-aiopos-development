package com.ultra.pos.api;

import android.support.annotation.VisibleForTesting;
import android.support.v4.content.res.FontResourcesParserCompat;

import androidx.browser.browseractions.BrowserActionsIntent;

import com.ultra.pos.model.PostTransaksiListModel;
import com.ultra.pos.model.PostTransaksiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface BaseApiInterface {

    @FormUrlEncoded
    @POST("api_pos/login.php")
    Call<ResponseBody> postLogin(@FieldMap HashMap<String, String> params, @Field("email_user") String emailUser,
                                 @Field("password_user") String passwordUser);

    @FormUrlEncoded
    @POST("api_pos/pelanggan_get_list.php")
    Call<ResponseBody> listPelanggan(@FieldMap HashMap<String, String> params, @Field("idbusiness") String idBusiness,
                                     @Field("idoutlet") String idOutlet);

    @FormUrlEncoded
    @POST("api_pos/pelanggan_get_detail.php")
    Call<ResponseBody> detailPelanggan(@FieldMap HashMap<String, String> params, @Field("idctm") String idPelanggan);

    @FormUrlEncoded
    @POST("api_pos/pelanggan_tambah.php")
    Call<ResponseBody> insertPelanggan(@FieldMap HashMap<String, String> params, @Field("idbusiness") String idBusiness,
                                       @Field("idoutlet") String idOutlet, @Field("province_id") String province_id,
                                       @Field("regencies_id") String regencies_id, @Field("district_id") String district_id,
                                       @Field("nama_pelanggan") String nama_pelanggan, @Field("email_pelanggan") String email_pelanggan,
                                       @Field("telp_pelanggan") String telp_pelanggan, @Field("telepon_pelanggan2") String telepon_pelanggan2);
    @FormUrlEncoded
    @POST("api_pos/pelanggan_update.php")
    Call<ResponseBody> updatePelanggan(@FieldMap HashMap<String, String> params, @Field("idctm") String idPelanggan,
                                       @Field("province_id") String province_id,
                                       @Field("regencies_id") String regencies_id, @Field("district_id") String district_id,
                                       @Field("nama_pelanggan") String nama_pelanggan, @Field("email_pelanggan") String email_pelanggan,
                                       @Field("telp_pelanggan") String telp_pelanggan, @Field("telepon_pelanggan2") String telepon_pelanggan2);

    @GET("api_pos/wilayah_get_provinsi.php")
    Call<ResponseBody> getProvinsi();

    @FormUrlEncoded
    @POST("api_pos/wilayah_get_kab_kec.php")
    Call<ResponseBody> getKecamatan(@FieldMap HashMap<String, String> params,
                                    @Field("id") String id, @Field("pilih") String pilih);

    @FormUrlEncoded
    @POST("api_pos/get_produk.php")
    Call<ResponseBody> getProduk(@FieldMap HashMap<String, String> params,
                                 @Field("idbusiness") String idBusiness, @Field("idtb") String idTB, @Field("idoutlet") String idOutlet);

    @FormUrlEncoded
    @POST("api_pos/sales_type_get_list.php")
    Call<ResponseBody> getSalesType(@FieldMap HashMap<String, String> params,
                                    @Field("idbusiness") String idBusiness, @Field("idoutlet") String idOutlet);

    @FormUrlEncoded
    @POST("api_pos/payment_setup_list.php")
    Call<ResponseBody> getEDC(@FieldMap HashMap<String, String> params,
                                    @Field("idbusiness") String idBusiness, @Field("idoutlet") String idOutlet);

    @FormUrlEncoded
    @POST("api_pos/transaksi_riwayat.php")
    Call<ResponseBody> getListRiwayat(@FieldMap HashMap<String, String> params, @Field("iduser") String idUser);

    @FormUrlEncoded
    @POST("api_pos/transaksi_detail_riwayat.php")
    Call<ResponseBody> getdetailRiwayat(@FieldMap HashMap<String, String> params, @Field("idtransHD") String idtrans);


    @POST("api_pos/transaksi.php")
    Call<ResponseBody> sendTransaksi(@Body PostTransaksiModel postTransaksiModel);

//    @POST("api_pos/transaksi.php")
//    Call<Void> sendTransaksi(@HeaderMap Map<String, String> params, @HeaderMap Map<String, Boolean> ListProduk , @Body PostTransaksiModel postTransaksiModel);
}
