package com.aio.pos.activity;

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
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aio.pos.R;
import com.aio.pos.api.SharedPrefManager;

public class PengaturanActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    LinearLayout llProfilAkun,llPrinter,llBantuan;
    Button logout;
    SharedPrefManager pref;
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

        if (width >= 1920 && height >= 1200){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_pengaturan_layout);
        NavigationView navigationView = findViewById(R.id.nav_pengaturan_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorGray4d4d4d));
        navigationView.setNavigationItemSelectedListener(this);

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

        } else if (id == R.id.nav_pengaturan) {
            startActivity(new Intent(this, PengaturanActivity.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_pengaturan_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
