package com.aio.pos.api;

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
    @POST("Master/login")
    Call<ResponseBody> postLogin(@FieldMap HashMap<String, String> params, @Field("email_user") String emailUser,
                                 @Field("password_user") String passwordUser);

    @POST("Master/wilayah_provinsi")
    Call<ResponseBody> getProvinsi();

    @FormUrlEncoded
    @POST("Master/wilayah_kab_kec")
    Call<ResponseBody> getKecamatan(@FieldMap HashMap<String, String> params,
                                    @Field("id") String id, @Field("pilih") String pilih);

    @FormUrlEncoded
    @POST("Master/get_produk")
    Call<ResponseBody> getProduk(@FieldMap HashMap<String, String> params,
                                 @Field("idbusiness") String idBusiness, @Field("idtb") String idTB, @Field("idoutlet") String idOutlet);

    @FormUrlEncoded
    @POST("Master/sales_type_get_list")
    Call<ResponseBody> getSalesType(@FieldMap HashMap<String, String> params,
                                    @Field("idbusiness") String idBusiness, @Field("idoutlet") String idOutlet);

    @FormUrlEncoded
    @POST("Master/payment_setup_list")
    Call<ResponseBody> getEDC(@FieldMap HashMap<String, String> params,
                              @Field("idbusiness") String idBusiness, @Field("idoutlet") String idOutlet);

    @FormUrlEncoded
    @POST("Pelanggan/get_list")
    Call<ResponseBody> listPelanggan(@FieldMap HashMap<String, String> params, @Field("idbusiness") String idBusiness,
                                     @Field("idoutlet") String idOutlet);

    @FormUrlEncoded
    @POST("Pelanggan/get_detail")
    Call<ResponseBody> detailPelanggan(@FieldMap HashMap<String, String> params, @Field("idctm") String idPelanggan);

    @FormUrlEncoded
    @POST("Pelanggan/delete")
    Call<ResponseBody> hapusPelanggan(@FieldMap HashMap<String, String> params, @Field("idctm") String idPelanggan);

    @FormUrlEncoded
    @POST("Pelanggan/tambah")
    Call<ResponseBody> insertPelanggan(@FieldMap HashMap<String, String> params, @Field("idbusiness") String idBusiness,
                                       @Field("idoutlet") String idOutlet, @Field("province_id") String province_id,
                                       @Field("regencies_id") String regencies_id, @Field("district_id") String district_id,
                                       @Field("nama_pelanggan") String nama_pelanggan, @Field("email_pelanggan") String email_pelanggan,
                                       @Field("telp_pelanggan") String telp_pelanggan, @Field("telepon_pelanggan2") String telepon_pelanggan2);
    @FormUrlEncoded
    @POST("Pelanggan/update")
    Call<ResponseBody> updatePelanggan(@FieldMap HashMap<String, String> params, @Field("idctm") String idPelanggan,
                                       @Field("province_id") String province_id,
                                       @Field("regencies_id") String regencies_id, @Field("district_id") String district_id,
                                       @Field("nama_pelanggan") String nama_pelanggan, @Field("email_pelanggan") String email_pelanggan,
                                       @Field("telp_pelanggan") String telp_pelanggan, @Field("telepon_pelanggan2") String telepon_pelanggan2);

    @FormUrlEncoded
    @POST("Transaksi/riwayat")
    Call<ResponseBody> getListRiwayat(@FieldMap HashMap<String, String> params, @Field("iduser") String idUser);

    @FormUrlEncoded
    @POST("Transaksi/detail")
    Call<ResponseBody> getdetailRiwayat(@FieldMap HashMap<String, String> params, @Field("idtransHD") String idtrans);

    @FormUrlEncoded
    @POST("Transaksi/refund")
    Call<ResponseBody> refundTransaksi(@FieldMap HashMap<String, String> params, @Field("idtransHD") String idtrans, @Field("ket_refund") String ketRefund);

    @FormUrlEncoded
    @POST("Transaksi")
    Call<ResponseBody> sendTransaksi(@Field("idtb") String idtb, @Field("idbusiness") String idBusiness, @Field("idcop") String idCop, @Field("idoutlet") String idOutlet, @Field("idctm") String idCtm,
                                     @Field("noinv_transHD") String noInvTransHD, @Field("diskon") String diskon, @Field("status_transHD") String statusTransHD, @Field("produklist") String produkList,
                                     @Field("iduser") String idUser, @Field("idpay") String idPay, @Field("idsaltype") String idSaltype, @Field("idrefund") String idRefund, @Field("type_trans") String typeTrans,
                                     @Field("ket_refund") String ketRefund, @Field("total_transHD") String totalTransHD, @Field("idtax") String idTax, @Field("pay_transHD") String payTransHD, @Field("cashback_transHD") String cashbackTransHD);

//    @POST("api_pos/transaksi.php")
//    Call<Void> sendTransaksi(@HeaderMap Map<String, String> params, @HeaderMap Map<String, Boolean> ListProduk , @Body PostTransaksiModel postTransaksiModel);
}
