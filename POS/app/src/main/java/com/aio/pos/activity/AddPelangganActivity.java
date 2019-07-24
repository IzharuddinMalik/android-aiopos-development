package com.aio.pos.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.aio.pos.R;
import com.aio.pos.api.APIUrl;
import com.aio.pos.api.BaseApiInterface;
import com.aio.pos.model.KabupatenModel;
import com.aio.pos.model.ProvinsiModel;
import com.aio.pos.api.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPelangganActivity extends AppCompatActivity {

    ImageView ivAddPelangganBtnKembali;
    private BaseApiInterface mApiInterface;
    Button submitTambah;
    SharedPrefManager pref;
    TextInputEditText nama,telp,telp2, email;
    Spinner spProvinsi,spKabupaten,spKecamatan;
    String idProv,idKab,idKec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pelanggan);

        spProvinsi=findViewById(R.id.spTambahProvinsi);
        spKabupaten=findViewById(R.id.spTambahKabupaten);
        spKecamatan=findViewById(R.id.spTambahKecamatan);

        nama=findViewById(R.id.tietMenuTambahPelangganNamaPelanggan);
        telp=findViewById(R.id.tietMenuTambahPelangganNoTelpPelanggan);
        telp2=findViewById(R.id.tietMenuTambahPelangganNoTelpPelanggan2);
        email=findViewById(R.id.tietMenuTambahPelangganEmailPelanggan);
        submitTambah=findViewById(R.id.btnMenuTambahPelangganSimpanPelanggan);

        getAllDataProvinsi();

        ivAddPelangganBtnKembali = findViewById(R.id.ivMenuTambahPelangganArrowBack);
        ivAddPelangganBtnKembali.setOnClickListener(v -> {
            startActivity(new Intent(this, PelangganActivity.class));
        });

        submitTambah.setOnClickListener(v -> {
            tambahRequest();

            pref = new SharedPrefManager(this);
            HashMap<String, String> user = pref.getUserDetails();
            String idctm = getIntent().getStringExtra("idctm");

            Intent intent = new Intent(this, PelangganActivity.class);
            intent.putExtra("idctm", idctm);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        });
    }

    public void onBackPressed(){
        startActivity(new Intent(this, PelangganActivity.class));
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
                            final int id=i;
                            JSONObject objProv = array.getJSONObject(i);
                            ProvinsiModel wilayahModel = new ProvinsiModel(
                                    idProvinsi[i] = objProv.getString("id"),
                                    namaProvinsi[i] = objProv.getString("name")
                            );

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddPelangganActivity.this, android.R.layout.select_dialog_singlechoice, namaProvinsi);

                            spProvinsi=findViewById(R.id.spTambahProvinsi);
                            spProvinsi.setAdapter(adapter);
                            spProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    idProv = idProvinsi[position];
                                    getAllDataKab(idProv);
                                    Log.i("Klik",""+idProv);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

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

        HashMap<String, String> params = new HashMap<>();
        params.put("id",idProv);
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

                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AddPelangganActivity.this, android.R.layout.select_dialog_singlechoice, namaKabupaten);
                            spKabupaten=findViewById(R.id.spTambahKabupaten);
                            spKabupaten.setAdapter(adapter1);
                            spKabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    idKab = idKabupaten[position];
                                    getAllDataKec(idKab);
                                    Log.i("Klik Kabupaten",""+idKab);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

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

    public void getAllDataKec(String idKab){

        HashMap<String, String> params = new HashMap<>();
        params.put("id",idKab);
        params.put("pilih", "2");
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getKecamatan(params,idKab,"2").enqueue(new Callback<ResponseBody>() {
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
                        String[] idKecamatan = new String[array.length()];
                        for (int i = 0; i< array.length();i++){
                            JSONObject objProv = array.getJSONObject(i);
                            KabupatenModel kabupatenModel = new KabupatenModel(
                                    idKecamatan[i] = objProv.getString("id"),
                                    namaKecamatan[i] = objProv.getString("name")
                            );

                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AddPelangganActivity.this, android.R.layout.select_dialog_singlechoice, namaKecamatan);
                            spKecamatan=findViewById(R.id.spTambahKecamatan);
                            spKecamatan.setAdapter(adapter2);
                            spKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    idKec = idKecamatan[position];
                                    Log.i("Klik Kecamatan",""+idKec);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

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

    public void tambahRequest(){
        String namaPelanggan = nama.getText().toString();
        String teleponPelanggan = telp.getText().toString();
        String telepon2Pelanggan = telp2.getText().toString();
        String emailPelanggan = email.getText().toString();

        if (namaPelanggan.isEmpty()){
            nama.setError("Wajib Diisi!");
            nama.requestFocus();
        }
        if (teleponPelanggan.isEmpty()){
            telp.setError("Wajib Diisi!");
            telp.requestFocus();
        }
        if (emailPelanggan.isEmpty()){
            email.setError("Wajib Diisi!");
            email.requestFocus();
        }

        if(namaPelanggan.length()!=0 && teleponPelanggan.length()!=0){
            pref = new SharedPrefManager(this);
            HashMap<String, String> user = pref.getUserDetails();
            String idbusiness = user.get(SharedPrefManager.ID_BUSINESS);
            String idoutlet = user.get(SharedPrefManager.ID_OUTLET);

            HashMap<String, String> params = new HashMap<>();
            params.put("idbusiness", idbusiness);
            params.put("idoutlet", idoutlet);
            params.put("province_id", idProv);
            params.put("regencies_id", idKab);
            params.put("district_id", idKec);
            params.put("nama_pelanggan", namaPelanggan);
            params.put("email_pelanggan", emailPelanggan);
            params.put("telp_pelanggan", teleponPelanggan);
            params.put("telepon_pelanggan2", ""+telepon2Pelanggan);
            mApiInterface = APIUrl.getAPIService();
            mApiInterface.insertPelanggan(params,idbusiness,idoutlet,idProv,idKab,idKec,namaPelanggan,
                    emailPelanggan,teleponPelanggan,telepon2Pelanggan).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        try{
                            String result = response.body().string();
                            JSONObject jsonResult = new JSONObject(result);

                            Log.i("Isi",
                                    "ID Bisnis:"+idbusiness+
                                            "ID Outlet"+idoutlet+
                                            "province_id"+idProv+
                                            "regencies_id"+idKab+
                                            "district_id"+idKec+
                                            "nama_pelanggan" +namaPelanggan+
                                            "email_pelanggan"+emailPelanggan+
                                            "telp_pelanggan"+teleponPelanggan);

                            Log.i("Hasil",jsonResult.getString("message"));

                        } catch (IOException e){
                            e.printStackTrace();
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    } else{
                        Log.i("Hasil",response.toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i("Hasil",t.toString());
                }
            });
        }
    }

}
