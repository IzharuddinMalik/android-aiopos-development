package com.ultra.pos.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

import com.ultra.pos.R;
import com.ultra.pos.adapter.AdapterPilihPelanggan;
import com.ultra.pos.adapter.AdapterRiwayatTransaksi;
import com.ultra.pos.adapter.AdapterTransaksiTersimpan;
import com.ultra.pos.api.APIUrl;
import com.ultra.pos.api.BaseApiInterface;
import com.ultra.pos.api.SharedPrefManager;
import com.ultra.pos.model.PelangganModel;
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

public class RiwayatTerakhirActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private List<TransaksiModel> listTransaksi;
    private AdapterRiwayatTransaksi adapter;
    RecyclerView recTransaksi;
    TextView JumlahTransaki,JenisPembayaran;
    private SharedPrefManager pref;
    private String iduser;
    private BaseApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_terakhir);
        Toolbar toolbar = findViewById(R.id.riwayat_toolbar);
        setSupportActionBar(toolbar);

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

        DrawerLayout drawer = findViewById(R.id.drawer_riwayat_layout);
        NavigationView navigationView = findViewById(R.id.nav_riwayat_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorGray4d4d4d));
        navigationView.setNavigationItemSelectedListener(this);

        recTransaksi = findViewById(R.id.rvRiwayatTransaksi);
        JumlahTransaki=findViewById(R.id.tvJumlahRiwayatTransaksi);
        getAlllistriwayat();

//        listTransaksi = new ArrayList<>();
//        listTransaksi.clear();
//
//        listTransaksi.add(0, new TransaksiModel("15000", "2", "Selesai", "15.20"));
//        listTransaksi.add(1, new TransaksiModel("15000", "1", "Selesai", "10.00"));
//        listTransaksi.add(2, new TransaksiModel("15000", "3", "Selesai", "13.30"));
//
//        adapter = new AdapterRiwayatTransaksi(this,listTransaksi);
//        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//        recTransaksi.setLayoutManager(mLayoutManager);
//        recTransaksi.setItemAnimator(new DefaultItemAnimator());
//        recTransaksi.setItemViewCacheSize(listTransaksi.size());
//        recTransaksi.setDrawingCacheEnabled(true);
//        recTransaksi.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//        recTransaksi.setAdapter(adapter);
//        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.animation_slide_from_right);
//        recTransaksi.setLayoutAnimation(animation);
//        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_transaksi) {
            startActivity(new Intent(this, Dashboard.class));
            finish();
        } else if (id == R.id.nav_transhistory) {

        } else if (id == R.id.nav_shiftkerja) {
            startActivity(new Intent(this, ShiftActivity.class));
            finish();
        } else if (id == R.id.nav_pengaturan) {
            startActivity(new Intent(this, PengaturanActivity.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_riwayat_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getAlllistriwayat(){
        pref = new SharedPrefManager(this);
        HashMap<String, String> user = pref.getUserDetails();
        String idBusiness = user.get(SharedPrefManager.ID_USER);
        iduser = idBusiness;

        listTransaksi = new ArrayList<>();
        listTransaksi.clear();

        HashMap<String, String> params = new HashMap<>();
        params.put("iduser",iduser);
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getListRiwayat(params,iduser).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);
                        JSONArray array = jsonResult.getJSONArray("info");
                        Log.i("Tes",array.toString());
                        Log.i("Panjang",""+array.length());

                        JumlahTransaki.setText(array.length()+" Transaksi");

                        for (int i = 0; i<array.length(); i++){
                            JSONObject objKategori = array.getJSONObject(i);

                            listTransaksi.add(i, new TransaksiModel(""+objKategori.getString("idtransHD"),""+objKategori.getString("total_transHD"),"0",""+objKategori.getString("idrefund"),""+objKategori.getString("jam"),""+objKategori.getString("idpay")));
                        }
                        adapter = new AdapterRiwayatTransaksi(RiwayatTerakhirActivity.this,listTransaksi);
                        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RiwayatTerakhirActivity.this);
                        recTransaksi.setLayoutManager(mLayoutManager);
                        recTransaksi.setItemAnimator(new DefaultItemAnimator());
                        recTransaksi.setItemViewCacheSize(listTransaksi.size());
                        recTransaksi.setDrawingCacheEnabled(true);
                        recTransaksi.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                        recTransaksi.setAdapter(adapter);
                        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(RiwayatTerakhirActivity.this, R.anim.animation_slide_from_right);
                        recTransaksi.setLayoutAnimation(animation);
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
