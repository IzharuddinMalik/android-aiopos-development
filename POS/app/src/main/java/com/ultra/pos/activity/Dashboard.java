package com.ultra.pos.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ultra.pos.R;
import com.ultra.pos.adapter.TabAdapter;
import com.ultra.pos.api.APIConnect;
import com.ultra.pos.api.APIUrl;
import com.ultra.pos.api.BaseApiInterface;
import com.ultra.pos.api.SharedPrefManager;
import com.ultra.pos.model.Produk;
import com.ultra.pos.model.ProdukModel;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout tabLayout;
    TabAdapter adapter;
    ViewPager viewPager;
    ImageView ivKeranjang, ivOptionMenu, ivSearch, ivCancelDialogDiskon, ivCancelDialogNama;
    LinearLayout llDashboardBukaPelanggan,llDashboardLihatPesanan;
    SearchView svNamaProduk;
    int backpress;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    CardView cvDiskon, cvNama, cvBatalTransaksi;
    EditText edtDialogDiskonNilai, edtDialogNamaNilai;
    Button btnSimpanDialogDiskon, btnSimpanDialogNama;
    TextView tvDashboardNavNama;
    SharedPrefManager pref;
    BaseApiInterface mApiInterface;
    APIConnect apiConnect;
    String idbusiness, idtb, idoutlet;
    DashboardFragment dashboardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorGray4d4d4d));
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        tvDashboardNavNama = header.findViewById(R.id.tvDashboardNamaAkun);
        pref = new SharedPrefManager(Dashboard.this);
        HashMap<String, String> user = pref.getUserDetails();
        String nama = user.get(SharedPrefManager.NAMA_USER);
        tvDashboardNavNama.setText(Html.fromHtml("<b>" + nama+ "</b>"));
        idbusiness = user.get(SharedPrefManager.ID_BUSINESS);

        viewPager = findViewById(R.id.frameLayout);
        tabLayout = findViewById(R.id.tabs);
        ivKeranjang = findViewById(R.id.ivDashboardKeranjang);
        llDashboardLihatPesanan=findViewById(R.id.llDashboardLihatPesanan);

        ivSearch = findViewById(R.id.ivDashboardGambarSearch);
        svNamaProduk = findViewById(R.id.svDashboardNamaProduk);

        ivKeranjang.setOnClickListener(v -> {
            startActivity(new Intent(this,TransaksiTersimpanActivity.class));
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", "" + width);
        Log.d("height", "" + height);

        if (width == 720 && height == 1280){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            llDashboardLihatPesanan.setOnClickListener(v -> {
                startActivity(new Intent(this, RingkasanOrderActivity.class));
            });
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            ivOptionMenu = findViewById(R.id.ivDashboardMenuOptions);
            ivOptionMenu.setOnClickListener(v -> {
                dialogFormOptionsMenu();
            });
        }

        llDashboardBukaPelanggan = findViewById(R.id.llDashboardBukaPelanggan);
        llDashboardBukaPelanggan.setOnClickListener(v -> {
            startActivity(new Intent(this, PelangganActivity.class));
        });
        getAllListProduk();


    }

    public void dialogFormOptionsMenu(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_option_menu, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        cvDiskon = dialogView.findViewById(R.id.cvDashboardDiskon);
        cvNama = dialogView.findViewById(R.id.cvDashboardNama);
        cvBatalTransaksi = dialogView.findViewById(R.id.cvDashboardBatalTransaksi);

        cvDiskon.setOnClickListener(v -> {
            dialogMenuDiskon();
        });

        cvNama.setOnClickListener(v -> {
            dialogMenuNama();
        });

        dialog.show();
    }

    public void dialogMenuDiskon(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_diskon,null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        ivCancelDialogDiskon = dialogView.findViewById(R.id.ivCancelDialogDiskon);
        ivCancelDialogDiskon.setOnClickListener(v -> {
            startActivity(new Intent(this, Dashboard.class));
            this.finish();
        });

        btnSimpanDialogDiskon = dialogView.findViewById(R.id.btnDialogFormSimpan);
        btnSimpanDialogDiskon.setOnClickListener(v -> {
            startActivity(new Intent(this, Dashboard.class));
            this.finish();
        });

        dialog.show();
    }

    public void dialogMenuNama(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_nama, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        ivCancelDialogNama = dialogView.findViewById(R.id.ivCancelDialogNama);
        ivCancelDialogNama.setOnClickListener(v -> {
            startActivity(new Intent(this, Dashboard.class));
            this.finish();
        });

        btnSimpanDialogNama = dialogView.findViewById(R.id.btnDialogFormSimpanNama);
        btnSimpanDialogNama.setOnClickListener(v -> {
            startActivity(new Intent(this, Dashboard.class));
        });

        dialog.show();
    }

    public void getAllListProduk(){

        pref = new SharedPrefManager(Dashboard.this);
        HashMap<String, String> user = pref.getUserDetails();
        String idBusiness = user.get(SharedPrefManager.ID_BUSINESS);
        String idTb = user.get(SharedPrefManager.ID_TB);
        String idOutlet = user.get(SharedPrefManager.ID_OUTLET);
        idbusiness = idBusiness;
        idtb = idTb;
        idoutlet = idOutlet;

        mApiInterface = APIUrl.getAPIService();
        HashMap<String, String> params = new HashMap<>();
        params.put("idbusiness",idbusiness);
        params.put("idtb", idtb);
        params.put("idoutlet", idoutlet);
        mApiInterface.getProduk(params, idbusiness, idtb, idoutlet).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);
                        JSONArray array = jsonResult.getJSONArray("info");
                        String[] namaKategori = new String[array.length()];
                        String[] idKategori = new String[array.length()];
                        for (int i = 0; i<array.length(); i++){
                            JSONObject objKategori = array.getJSONObject(i);
                            ProdukModel produkModel = new ProdukModel(
                                    idKategori[i] = objKategori.getString("idkategori"),
                                    namaKategori[i] = objKategori.getString("nama_kategori"),
                                    objKategori.getString("produk")
                            );

                            Log.i("LISTPRODUK", "LISTNYA ->" + objKategori.getString("idkategori"));

                            pref = new SharedPrefManager(Dashboard.this );

                            adapter = new TabAdapter(getSupportFragmentManager());

                            for (int k = 0; k < array.length();k++){
                                adapter.addFragment(new DashboardFragment(), " " + namaKategori[k]);
                            }

                            viewPager.setAdapter(adapter);
                            tabLayout.setupWithViewPager(viewPager);
                            viewPager.setOffscreenPageLimit(1);
                            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                            if (tabLayout.getTabCount() == 2){
                                tabLayout.setTabMode(tabLayout.MODE_FIXED);
                            }else{
                                tabLayout.setTabMode(tabLayout.MODE_SCROLLABLE);
                            }
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

    @Override
    public void onBackPressed() {
        backpress = (backpress + 1);
        Toast.makeText(this, "Tekan Tombol Sekali Lagi", Toast.LENGTH_SHORT).show();

        if (backpress > 1){
            finish();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_transaksi) {
            startActivity(new Intent(this, Dashboard.class));
            finish();
        } else if (id == R.id.nav_transhistory) {
            startActivity(new Intent(this,RiwayatTerakhirActivity.class));
            finish();
        } else if (id == R.id.nav_shiftkerja) {
            startActivity(new Intent(this, ShiftActivity.class));
            finish();
        } else if (id == R.id.nav_pengaturan) {
            startActivity(new Intent(this, PengaturanActivity.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
