package com.ultra.pos.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

import com.ultra.pos.R;
import com.ultra.pos.adapter.AdapterPesanan;
import com.ultra.pos.adapter.AdapterRiwayatTransaksi;
import com.ultra.pos.api.APIUrl;
import com.ultra.pos.api.BaseApiInterface;
import com.ultra.pos.api.SharedPrefManager;
import com.ultra.pos.model.Produk;
import com.ultra.pos.model.ProdukModel;
import com.ultra.pos.model.TransaksiModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRiwayatTransaksiActivity extends AppCompatActivity {

    TextView total,jam,pelanggan,outlet,metode,kodetrans,tanggal;
    RecyclerView recPesanan;
    private List<Produk> listPesanan;
    private AdapterPesanan adapter;
    Button cetak,batal;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    private SharedPrefManager pref;
    private String idtrans;
    private BaseApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat_transaksi);

        total = findViewById(R.id.tvTotalDetailRiwayat);
        jam = findViewById(R.id.tvJamTransaksi);
        tanggal = findViewById(R.id.tvTanggalTransaksi);
        pelanggan = findViewById(R.id.tvDetailNamaPelangganRiwayat);
        outlet= findViewById(R.id.tvDetailNamaOutletRiwayat);
        metode=findViewById(R.id.tvDetailMetodePembayaranRiwayat);
        kodetrans=findViewById(R.id.tvDetailKodeTransaksiRiwayat);

//        total.setText(getIntent().getStringExtra("totalharga"));
//        jam.setText(getIntent().getStringExtra("jamtransaksi"));

        recPesanan = findViewById(R.id.rvDetailRiwayat);
        getAlldetailriwayat();
//        listPesanan = new ArrayList<>();
//        listPesanan.clear();

//        listPesanan.add(0, new ProdukModel("1", "Nasi", "3000", "Aceh"));
//        listPesanan.add(1, new ProdukModel("2", "Teh", "2000", "Aceh"));
//        listPesanan.add(2, new ProdukModel("3", "Ayam", "9000", "Aceh"));
//        listPesanan.add(3, new ProdukModel("4", "Gorengan", "1000", "Aceh"));

//        adapter = new AdapterPesanan(this, listPesanan);
//        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//        recPesanan.setLayoutManager(mLayoutManager);
//        recPesanan.setItemAnimator(new DefaultItemAnimator());
//        recPesanan.setItemViewCacheSize(listPesanan.size());
//        recPesanan.setDrawingCacheEnabled(true);
//        recPesanan.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//        recPesanan.setAdapter(adapter);
//        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.animation_slide_from_right);
//        recPesanan.setLayoutAnimation(animation);
//        adapter.notifyDataSetChanged();

        cetak=findViewById(R.id.btnCetakRiwayat);

        cetak.setOnClickListener(v -> {
            dialogPrint();
        });
    }

    public void dialogPrint(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_cetak,null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        dialog.show();
    }

    public void getAlldetailriwayat(){
        idtrans = getIntent().getStringExtra("idtrans");

        listPesanan = new ArrayList<>();
        listPesanan.clear();

        HashMap<String, String> params = new HashMap<>();
        params.put("idtransHD",idtrans);

        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getdetailRiwayat(params,idtrans).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);
                        JSONArray array = jsonResult.getJSONArray("info");

                        if(array.toString().equals("[]")){

                        }else{

                            for (int i = 0; i<array.length(); i++){
                                JSONObject objKategori = array.getJSONObject(i);
                                Log.i("Cek Isi",""+objKategori);
                                outlet.setText(objKategori.getString("name_outlet"));
                                metode.setText(objKategori.getString("nama_pay"));
                                kodetrans.setText(objKategori.getString("kode_transHD"));
                                tanggal.setText(objKategori.getString("tgl"));
                                jam.setText(objKategori.getString("jam"));
                                total.setText(objKategori.getString("total_transHD"));
                                pelanggan.setText(objKategori.getString("nama_pelanggan"));
//

                                JSONArray arrHarga = objKategori.getJSONArray("harga");
//                                Log.i("Isi",""+arrHarga);
//                                Log.i("Isi",""+arrHarga.length());

                                for(int j=0;j<arrHarga.length();j++){
                                    JSONObject objharga = arrHarga.getJSONObject(j);
                                    listPesanan.add(j, new Produk(""+objharga.getString("nama_produk"),""+objharga.getString("nama_variant"),""+objharga.getString("harga_satuan"),""+objharga.getString("qty")));
                                }
                                adapter = new AdapterPesanan(DetailRiwayatTransaksiActivity.this, listPesanan);
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DetailRiwayatTransaksiActivity.this);
                                recPesanan.setLayoutManager(mLayoutManager);
                                recPesanan.setItemAnimator(new DefaultItemAnimator());
                                recPesanan.setItemViewCacheSize(listPesanan.size());
                                recPesanan.setDrawingCacheEnabled(true);
                                recPesanan.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                                recPesanan.setAdapter(adapter);
                                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(DetailRiwayatTransaksiActivity.this, R.anim.animation_slide_from_right);
                                recPesanan.setLayoutAnimation(animation);
                                adapter.notifyDataSetChanged();
                            }
                        }

//                        for (int i = 0; i<array.length(); i++){
//                            JSONObject objKategori = array.getJSONObject(i);
//
//                            listPesanan.add(i, new Produk("3", ""+objKategori.getString("nama_Produk"), "9000", "Aceh","","",""));
//                        }

                    }catch (JSONException e){
                        e.printStackTrace();
                    } catch (IOException e){
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
