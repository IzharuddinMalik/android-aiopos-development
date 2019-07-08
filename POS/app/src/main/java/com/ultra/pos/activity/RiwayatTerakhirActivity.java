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

import com.ultra.pos.R;
import com.ultra.pos.adapter.AdapterRiwayatTransaksi;
import com.ultra.pos.adapter.AdapterTransaksiTersimpan;
import com.ultra.pos.model.TransaksiModel;

import java.util.ArrayList;
import java.util.List;

public class RiwayatTerakhirActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private List<TransaksiModel> listTransaksi;
    private AdapterRiwayatTransaksi adapter;
    RecyclerView recTransaksi;

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
        listTransaksi = new ArrayList<>();
        listTransaksi.clear();

        listTransaksi.add(0, new TransaksiModel("15000", "2", "Selesai", "15.20"));
        listTransaksi.add(1, new TransaksiModel("15000", "1", "Selesai", "10.00"));
        listTransaksi.add(2, new TransaksiModel("15000", "3", "Selesai", "13.30"));

        adapter = new AdapterRiwayatTransaksi(this,listTransaksi);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recTransaksi.setLayoutManager(mLayoutManager);
        recTransaksi.setItemAnimator(new DefaultItemAnimator());
        recTransaksi.setItemViewCacheSize(listTransaksi.size());
        recTransaksi.setDrawingCacheEnabled(true);
        recTransaksi.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recTransaksi.setAdapter(adapter);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.animation_slide_from_right);
        recTransaksi.setLayoutAnimation(animation);
        adapter.notifyDataSetChanged();
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
}
