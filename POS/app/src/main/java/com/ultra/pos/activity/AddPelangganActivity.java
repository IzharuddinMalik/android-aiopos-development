package com.ultra.pos.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.ultra.pos.R;
import com.ultra.pos.api.APIUrl;
import com.ultra.pos.api.BaseApiInterface;
import com.ultra.pos.model.PelangganModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPelangganActivity extends AppCompatActivity {

    AutoCompleteTextView actvProvinsiPelanggan, actvKabupatenPelanggan, actvKecamatanPelanggan;
    ImageView ivAddPelangganBtnKembali;
    String[] provinsi = {"Aceh", "Sumatera Utara", "Sumatera Barat", "Sumatera Selatan", "Lampung", "Riau", "Banten", "DKI Jakarta",
            "Jawa Barat", "DI Yogyakarta", "Jawa Tengah", "Jawa Timur"};
    String[] kabupaten = {"Nanggroe Aceh Darussalam", "Medan", "Padang", "Palembang", "Lampung", "Pekanbaru", "Tangerang", "Jakarta Pusat",
            "Bandung", "Kota Yogyakarta", "Semarang", "Surabaya"};
    String[] kecamatan = {"Tulungagung", "Prambanan", "Kalasan", "Berbah", "Ngaglik", "UmbulHarjo", "Depok", "Maguwoharjo", "Turi"};
    private BaseApiInterface mApiInterface;
    Spinner Provinsi;
    ArrayList<String> idlist=new ArrayList<String>();
    List<String> namaProvinsi=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pelanggan);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, provinsi);
//        actvProvinsiPelanggan = findViewById(R.id.actvMenuTambahPelangganProvinsiPelanggan);
//        actvProvinsiPelanggan.setThreshold(1);
//        actvProvinsiPelanggan.setAdapter(adapter);
//
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, kabupaten);
//        actvKabupatenPelanggan = findViewById(R.id.actvMenuTambahPelangganKabupatenPelanggan);
//        actvKabupatenPelanggan.setThreshold(1);
//        actvKabupatenPelanggan.setAdapter(adapter1);
//
//        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, kecamatan);
//        actvKecamatanPelanggan = findViewById(R.id.actvMenuTambahPelangganKecamatanPelanggan);
//        actvKecamatanPelanggan.setThreshold(1);
//        actvKecamatanPelanggan.setAdapter(adapter2);
        Provinsi=findViewById(R.id.spMenuTambahProvinsi);
//        getProvinsi();

        ivAddPelangganBtnKembali = findViewById(R.id.ivMenuTambahPelangganArrowBack);
        ivAddPelangganBtnKembali.setOnClickListener(v -> {
            startActivity(new Intent(this, PelangganActivity.class));
        });
    }

    public void onBackPressed(){
        startActivity(new Intent(this, PelangganActivity.class));
    }

//    public void getProvinsi(){
//        mApiInterface = APIUrl.getAPIService();
//        mApiInterface.getProvinsi().enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()){
//                    try{
//                        String result = response.body().string();
//                        JSONObject jsonResult = new JSONObject(result);
//                        JSONArray array = jsonResult.getJSONArray("info");
//                        Log.i("Tes",result);
//                        Log.i("Panjang",""+array.length());
//
//                        for (int i = 0; i<array.length(); i++){
//                            JSONObject objKategori = array.getJSONObject(i);
//                            String nama = objKategori.getString("name");
//                            namaProvinsi.add(nama);
//                        }
//
//                        namaProvinsi.add(0, "- SELECT TYPE -");
//
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, namaProvinsi);
//                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        Provinsi.setAdapter(adapter);
//                    }catch (JSONException e){
//                        e.printStackTrace();
//                    } catch (IOException e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//    }
}
