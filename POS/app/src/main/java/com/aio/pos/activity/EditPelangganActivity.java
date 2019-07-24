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

public class EditPelangganActivity extends AppCompatActivity {

    ImageView ivEditBtnKembali;
    BaseApiInterface mApiInterface;
    Spinner spProvinsi,spKabupaten,spKecamatan;
    TextInputEditText nama,telp,telp2, email;
    String idProv,idKab,idKec;
    Button submitedit;
    SharedPrefManager pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pelanggan);

        nama=findViewById(R.id.tietMenuEditPelangganNamaPelanggan);
        telp=findViewById(R.id.tietMenuEditPelangganNoTelpPelanggan);
        telp2=findViewById(R.id.tietMenuEditPelangganNoTelpPelanggan2);
        email=findViewById(R.id.tietMenuEditPelangganEmailPelanggan);
        submitedit=findViewById(R.id.btnMenuEditPelangganSimpanPelanggan);

        getDataEdit();
        getAllDataProvinsi();

        ivEditBtnKembali = findViewById(R.id.ivMenuEdtiPelangganArrowBack);
        ivEditBtnKembali.setOnClickListener(v -> {
            pref = new SharedPrefManager(this);
            HashMap<String, String> user = pref.getUserDetails();
            String idctm = getIntent().getStringExtra("idctm");

            Intent intent = new Intent(this, DetailPelangganActivity.class);
            intent.putExtra("idctm", idctm);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        });

        submitedit.setOnClickListener(v -> {
            editRequest();

            pref = new SharedPrefManager(this);
            HashMap<String, String> user = pref.getUserDetails();
            String idctm = getIntent().getStringExtra("idctm");

            Intent intent = new Intent(this, DetailPelangganActivity.class);
            intent.putExtra("idctm", idctm);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        });
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


                            pref = new SharedPrefManager(EditPelangganActivity.this);
                            pref.createSessionWilayah(wilayahModel);

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditPelangganActivity.this, android.R.layout.select_dialog_singlechoice, namaProvinsi);

                            spProvinsi=findViewById(R.id.spEditProvinsi);
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

        Log.i("IDPROVINSI", "ID PROVINSI -> " + idProv);

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

                            pref = new SharedPrefManager(EditPelangganActivity.this);
                            pref.createSessionKabupaten(kabupatenModel);

                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(EditPelangganActivity.this, android.R.layout.select_dialog_singlechoice, namaKabupaten);
                            spKabupaten=findViewById(R.id.spEditKabupaten);
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

                            pref = new SharedPrefManager(EditPelangganActivity.this);
                            pref.createSessionKabupaten(kabupatenModel);

                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(EditPelangganActivity.this, android.R.layout.select_dialog_singlechoice, namaKecamatan);
                            spKecamatan=findViewById(R.id.spEditKecamatan);
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

    public void onBackPressed(){
        startActivity(new Intent(this, DetailPelangganActivity.class));
    }

    public void getDataEdit(){
        pref = new SharedPrefManager(this);
        HashMap<String, String> user = pref.getUserDetails();
        String idctm = getIntent().getStringExtra("idctm");

        mApiInterface = APIUrl.getAPIService();
        HashMap<String, String> params = new HashMap<>();
        params.put("idctm",idctm);
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.detailPelanggan(params,idctm).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);
                        JSONArray array = jsonResult.getJSONArray("info");

                        for (int i = 0; i<array.length(); i++){
                            JSONObject objKategori = array.getJSONObject(i);

                            String namaPelanggan=objKategori.getString("nama_pelanggan");
                            String telpPelanggan=objKategori.getString("telp_pelanggan");
                            String telpPelanggan2=objKategori.getString("telepon_pelanggan2");
                            String emailPelangan=objKategori.getString("email_pelanggan");

                            nama.setText(namaPelanggan);
                            telp.setText(telpPelanggan);
                            telp2.setText(telpPelanggan2);
                            email.setText(emailPelangan);

                        }
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

    public void editRequest(){
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


        pref = new SharedPrefManager(this);
        HashMap<String, String> user = pref.getUserDetails();
        String idctm = getIntent().getStringExtra("idctm");

        HashMap<String, String> params = new HashMap<>();
        params.put("idctm", idctm);
        params.put("province_id", idProv);
        params.put("regencies_id", idKab);
        params.put("district_id", idKec);
        params.put("nama_pelanggan", namaPelanggan);
        params.put("email_pelanggan", emailPelanggan);
        params.put("telp_pelanggan", teleponPelanggan);
        params.put("telepon_pelanggan2", telepon2Pelanggan);
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.updatePelanggan(params,idctm,idProv,idKab,idKec,namaPelanggan,emailPelanggan,teleponPelanggan,
                telepon2Pelanggan).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);

                        Log.i("Hasil",jsonResult.getString("message"));

                    } catch (IOException e){
                        e.printStackTrace();
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                } else{

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
