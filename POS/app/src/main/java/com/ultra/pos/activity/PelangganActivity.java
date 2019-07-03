package com.ultra.pos.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.ultra.pos.R;
import com.ultra.pos.adapter.AdapterPilihPelanggan;
import com.ultra.pos.api.APIUrl;
import com.ultra.pos.api.BaseApiInterface;
import com.ultra.pos.api.SharedPrefManager;
import com.ultra.pos.model.PelangganModel;

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

public class PelangganActivity extends AppCompatActivity {

    RecyclerView recPelanggan;
    private List<PelangganModel> dataPelanggan;
    private AdapterPilihPelanggan adapter;
    ImageView ivMenuPelangganKembali, ivMenuPelangganAddPelanggan;
    private SharedPrefManager pref;
    private String idbusiness,idoutlet;
    private BaseApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelanggan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ivMenuPelangganKembali = findViewById(R.id.ivMenuPelangganArrowBack);
        ivMenuPelangganAddPelanggan = findViewById(R.id.ivMenuPelangganAddPelanggan);

        ivMenuPelangganKembali.setOnClickListener(v -> {
            startActivity(new Intent(this, Dashboard.class));
        });

        ivMenuPelangganAddPelanggan.setOnClickListener(v -> {
            startActivity(new Intent(this, AddPelangganActivity.class));
        });

        recPelanggan = findViewById(R.id.rvMenuPelangganDaftarPelanggan);

        getAlllistpelanggan();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", "" + width);
        Log.d("height", "" + height);

        if (width >= 1920 && height >= 1200){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


    }

    public void onBackPressed(){
        startActivity(new Intent(this, Dashboard.class));
    }
    
    public void getAlllistpelanggan(){
        pref = new SharedPrefManager(this);
        HashMap<String, String> user = pref.getUserDetails();
        String idBusiness = user.get(SharedPrefManager.ID_BUSINESS);
        String idOutlet = user.get(SharedPrefManager.ID_OUTLET);
        idbusiness = idBusiness;
        idoutlet = idOutlet;

        dataPelanggan = new ArrayList<>();
        dataPelanggan.clear();

        HashMap<String, String> params = new HashMap<>();
        params.put("idbusiness",idbusiness);
        params.put("idOulet",idoutlet);
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.listPelanggan(params,idbusiness,idoutlet).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);
                        JSONArray array = jsonResult.getJSONArray("info");
                        Log.i("Tes",array.toString());
                        Log.i("Panjang",""+array.length());

                        String[] idPelanggan = new String[array.length()];
                        String[] namaPelanggan = new String[array.length()];
                        String[] telpPelanggan = new String[array.length()];
                        String[] emailPelangan = new String[array.length()];

                        PelangganModel pelangganModel=new PelangganModel("","","","");
                        for (int i = 0; i<array.length(); i++){
                            JSONObject objKategori = array.getJSONObject(i);
//                            pelangganModel(
//                                    objKategori.getString("idctm"),
//                                    objKategori.getString("nama_pelanggan"),
//                                    objKategori.getString("telp_pelanggan"),
//                                    objKategori.getString("email_pelanggan")
//                            );
                            idPelanggan[i]=objKategori.getString("idctm");
                            namaPelanggan[i]=objKategori.getString("nama_pelanggan");
                            telpPelanggan[i]=objKategori.getString("telp_pelanggan");
                            emailPelangan[i]=objKategori.getString("email_pelanggan");

                            dataPelanggan.add(i, new PelangganModel(idPelanggan[i],namaPelanggan[i],telpPelanggan[i],emailPelangan[i]));
                        }
                        adapter = new AdapterPilihPelanggan(PelangganActivity.this, dataPelanggan);
                        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PelangganActivity.this);
                        recPelanggan.setLayoutManager(mLayoutManager);
                        recPelanggan.setItemAnimator(new DefaultItemAnimator());
                        recPelanggan.setItemViewCacheSize(dataPelanggan.size());
                        recPelanggan.setDrawingCacheEnabled(true);
                        recPelanggan.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                        recPelanggan.setAdapter(adapter);
                        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(PelangganActivity.this, R.anim.animation_slide_from_right);
                        recPelanggan.setLayoutAnimation(animation);
                        adapter.notifyDataSetChanged();
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
