package com.aiopos.apps.activity;

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
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiopos.apps.R;
import com.aiopos.apps.api.SharedPrefManager;

import java.util.HashMap;

public class PengaturanActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    LinearLayout llProfilAkun,llPrinter,llBantuan;
    Button logout;
    SharedPrefManager pref;
    TextView tvDashboardNavNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);
        Toolbar toolbar = findViewById(R.id.pengaturan_toolbar);
        setSupportActionBar(toolbar);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", "" + width);
        Log.d("height", "" + height);

        if (getResources().getConfiguration().smallestScreenWidthDp == 360){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_pengaturan_layout);
        NavigationView navigationView = findViewById(R.id.nav_pengaturan_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorGray4d4d4d));
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        tvDashboardNavNama = header.findViewById(R.id.tvDashboardNamaAkun);
        pref = new SharedPrefManager(this);
        HashMap<String, String> user = pref.getUserDetails();
        String nama = user.get(SharedPrefManager.NAMA_USER);
        tvDashboardNavNama.setText(Html.fromHtml("<b>" + nama+ "</b>"));

        llProfilAkun=findViewById(R.id.llProfilAkun);
        llPrinter=findViewById(R.id.llPrinter);
        llBantuan=findViewById(R.id.llBantuan);
        logout=findViewById(R.id.btnLogout);

        pref=new SharedPrefManager(this);
        logout.setOnClickListener(v -> {
            pref.logout();
            finish();
        });

        llProfilAkun.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfilActivity.class));
        });

        llPrinter.setOnClickListener(v -> {
            startActivity(new Intent(this, PrinterActivity.class));
        });

        llBantuan.setOnClickListener(v -> {
            startActivity(new Intent(this, BantuanActivity.class));
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_transaksi) {
            startActivity(new Intent(this, Dashboard.class));
            finish();
        } else if (id == R.id.nav_transhistory) {
            startActivity(new Intent(this, RiwayatTerakhirActivity.class));
            finish();
        } else if (id == R.id.nav_pengaturan) {
            startActivity(new Intent(this, PengaturanActivity.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_pengaturan_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
