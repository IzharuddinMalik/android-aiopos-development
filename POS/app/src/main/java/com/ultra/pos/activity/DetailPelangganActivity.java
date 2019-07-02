package com.ultra.pos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ultra.pos.R;
import com.ultra.pos.api.APIUrl;
import com.ultra.pos.api.BaseApiInterface;
import com.ultra.pos.api.SharedPrefManager;
import com.ultra.pos.model.PelangganModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPelangganActivity extends AppCompatActivity {

    TextView tvDetailNamaPelanggan, tvDetailNoTelpPelanggan, tvDetailEmailPelanggan, tvDetailNamaProvPelanggan, tvDetailNamaKecPelanggan,
            tvDetailNamaKabKotPelanggan,tvDetailIDPelanggan;
    ImageView ivDetailBtnEditPelanggan, ivDetailBtnKembali;
    private SharedPrefManager pref;
    private BaseApiInterface mApiInterface;
    String idPelanggan ,idOutlet ,namaPelanggan ,telpPelanggan ,emailPelangan ,provinsiPelanggan ,kabupatenPelanggan ,kecamatanPelanggan,telpPelanggan2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pelanggan);

        tvDetailNamaPelanggan = findViewById(R.id.tvMenuDetailPelangganNamaPelanggan);
        tvDetailNoTelpPelanggan = findViewById(R.id.tvMenuDetailPelangganNoTelpPelanggan);
        tvDetailEmailPelanggan = findViewById(R.id.tvMenuDetailPelangganEmailPelanggan);
        tvDetailNamaProvPelanggan = findViewById(R.id.tvMenuDetailPelangganNamaProvinsiMember);
        tvDetailNamaKabKotPelanggan = findViewById(R.id.tvMenuDetailPelangganNamaKabKotMember);
        tvDetailNamaKecPelanggan = findViewById(R.id.tvMenuDetailPelangganNamaKecamatanMember);
        tvDetailIDPelanggan=findViewById(R.id.tvMenuDetailPelangganNoIDMember);
        ivDetailBtnEditPelanggan = findViewById(R.id.ivMenuDetailPelangganEditPelanggan);
        ivDetailBtnKembali = findViewById(R.id.ivMenuDetailPelangganArrowBack);

        getDetailPelanggan();

        ivDetailBtnEditPelanggan.setOnClickListener(v -> {
            startActivity(new Intent(this, EditPelangganActivity.class));
        });
        ivDetailBtnKembali.setOnClickListener(v -> {
            startActivity(new Intent(this, PelangganActivity.class));
        });
    }

    public void onBackPressed(){
        startActivity(new Intent(this, PelangganActivity.class));
    }

    public void getDetailPelanggan(){
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
                        Log.i("Tes",array.toString());
                        Log.i("Panjang",""+array.length());

                        for (int i = 0; i<array.length(); i++){
                            JSONObject objKategori = array.getJSONObject(i);
//                            PelangganModel pelangganModel=new PelangganModel(
//                                    objKategori.getString("idctm"),
//                                    objKategori.getString("nama_pelanggan"),
//                                    objKategori.getString("telp_pelanggan"),
//                                    objKategori.getString("email_pelanggan")
//                            );
                            idPelanggan=objKategori.getString("idctm");
                            idOutlet=objKategori.getString("idoutlet");
                            namaPelanggan=objKategori.getString("nama_pelanggan");
                            provinsiPelanggan=objKategori.getString("provinsi");
                            kabupatenPelanggan=objKategori.getString("kabupaten");
                            kecamatanPelanggan=objKategori.getString("kecamatan");
                            telpPelanggan=objKategori.getString("telp_pelanggan");
                            telpPelanggan2=objKategori.getString("telepon_pelanggan2");
                            emailPelangan=objKategori.getString("email_pelanggan");

                            tvDetailIDPelanggan.setText(idPelanggan);
                            tvDetailNamaPelanggan.setText(namaPelanggan);
                            tvDetailNoTelpPelanggan.setText(telpPelanggan);
                            tvDetailEmailPelanggan.setText(emailPelangan);
                            tvDetailNamaProvPelanggan.setText(provinsiPelanggan);
                            tvDetailNamaKabKotPelanggan.setText(kabupatenPelanggan);
                            tvDetailNamaKecPelanggan.setText(kecamatanPelanggan);

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
}
