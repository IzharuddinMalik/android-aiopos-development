package com.ultra.pos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.ultra.pos.R;
import com.ultra.pos.api.APIConnect;
import com.ultra.pos.api.APIUrl;
import com.ultra.pos.api.BaseApiInterface;
import com.ultra.pos.api.SharedPrefManager;
import com.ultra.pos.model.KabupatenModel;
import com.ultra.pos.model.KecamatanModel;
import com.ultra.pos.model.ProvinsiModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPelangganActivity extends AppCompatActivity {

    AutoCompleteTextView actvEditProvinsiPelanggan, actvEditKabupatenPelanggan, actvEditKecamatanPelanggan;
    ImageView ivEditBtnKembali;
    BaseApiInterface mApiInterface;
    APIConnect apiConnect;
    SharedPrefManager pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pelanggan);

        ivEditBtnKembali = findViewById(R.id.ivMenuEdtiPelangganArrowBack);
        ivEditBtnKembali.setOnClickListener(v -> {
            startActivity(new Intent(this, DetailPelangganActivity.class));
        });

        getAllDataProvinsi();

        getAllDataKec();
    }

    public void getAllDataProvinsi(){

        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getProvinsi().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{

                        String result = response.body().string();

                        JSONObject jsonResult = new JSONObject(result);
                        jsonResult.getString("success");
                        jsonResult.getString("message");

                        JSONArray array = jsonResult.getJSONArray("info");
                        String[] namaProvinsi = new String[array.length()];
                        String[] idProvinsi = new String[array.length()];
                        for (int i = 0; i< array.length();i++){
                            JSONObject objProv = array.getJSONObject(i);
                            ProvinsiModel wilayahModel = new ProvinsiModel(
                                    idProvinsi[i] = objProv.getString("id"),
                                    namaProvinsi[i] = objProv.getString("name")
                            );

                            pref = new SharedPrefManager(EditPelangganActivity.this);
                            pref.createSessionWilayah(wilayahModel);

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditPelangganActivity.this, android.R.layout.select_dialog_singlechoice, namaProvinsi);
                            actvEditProvinsiPelanggan = findViewById(R.id.actvMenuEditPelangganProvinsiPelanggan);
                            actvEditProvinsiPelanggan.setThreshold(1);
                            actvEditProvinsiPelanggan.setAdapter(adapter);

                            actvEditProvinsiPelanggan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    getAllDataKab(idProvinsi[i]);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getAllDataKab(String idProv){

        pref = new SharedPrefManager(EditPelangganActivity.this);
        HashMap<String, String> wilayah = pref.getWilayahDetails();
        String idProvinsi = wilayah.get(SharedPrefManager.ID_WILAYAH);

        idProv = idProvinsi;

        Log.i("IDPROVINSI", "ID PROVINSI -> " + idProv);

        HashMap<String, String> params = new HashMap<>();
        params.put("id", idProv);
        params.put("pilih", "1");
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getKecamatan(params, idProv, "1").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{

                        String result = response.body().string();

                        JSONObject jsonResult = new JSONObject(result);
                        jsonResult.getString("success");
                        jsonResult.getString("message");

                        JSONArray array = jsonResult.getJSONArray("info");
                        String[] namaKabupaten = new String[array.length()];
                        String[] idKabupaten = new String[array.length()];
                        for (int i = 0; i< array.length();i++){
                            JSONObject objProv = array.getJSONObject(i);
                            KabupatenModel kabupatenModel = new KabupatenModel(
                                    idKabupaten[i] = objProv.getString("id"),
                                    namaKabupaten[i] = objProv.getString("name")
                            );

                            pref = new SharedPrefManager(EditPelangganActivity.this);
                            pref.createSessionKabupaten(kabupatenModel);

                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(EditPelangganActivity.this, android.R.layout.select_dialog_singlechoice, namaKabupaten);
                            actvEditKabupatenPelanggan = findViewById(R.id.actvMenuEditPelangganKabupatenPelanggan);
                            actvEditKabupatenPelanggan.setThreshold(1);
                            actvEditKabupatenPelanggan.setAdapter(adapter1);
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getAllDataKec(){
        pref = new SharedPrefManager(EditPelangganActivity.this);
        HashMap<String, String> wilayah = pref.getWilayahDetails();
        String idKabupaten = wilayah.get(SharedPrefManager.ID_KABUPATEN);

        HashMap<String, String> params = new HashMap<>();
        params.put("id",idKabupaten);
        params.put("pilih", "2");
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getKecamatan(params, idKabupaten, "2").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{

                        String result = response.body().string();

                        JSONObject jsonResult = new JSONObject(result);
                        jsonResult.getString("success");
                        jsonResult.getString("message");

                        JSONArray array = jsonResult.getJSONArray("info");
                        String[] namaKecamatan = new String[array.length()];
                        for (int i = 0; i< array.length();i++){
                            JSONObject objProv = array.getJSONObject(i);
                            KecamatanModel kecamatanModel = new KecamatanModel(
                                    objProv.getString("id"),
                                    namaKecamatan[i] = objProv.getString("name")
                            );

                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(EditPelangganActivity.this, android.R.layout.select_dialog_singlechoice, namaKecamatan);
                            actvEditKecamatanPelanggan = findViewById(R.id.actvMenuEditPelangganKecamatanPelanggan);
                            actvEditKecamatanPelanggan.setThreshold(1);
                            actvEditKecamatanPelanggan.setAdapter(adapter2);
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void onBackPressed(){
        startActivity(new Intent(this, DetailPelangganActivity.class));
    }
}
