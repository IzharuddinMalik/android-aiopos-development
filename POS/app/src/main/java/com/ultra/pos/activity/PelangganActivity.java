package com.ultra.pos.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.SearchView;

import com.ultra.pos.R;
import com.ultra.pos.adapter.AdapterPilihPelanggan;
import com.ultra.pos.model.PelangganModel;

import java.util.ArrayList;
import java.util.List;

public class PelangganActivity extends AppCompatActivity {

    RecyclerView recPelanggan;
    private List<PelangganModel> dataPelanggan;
    private AdapterPilihPelanggan adapter;
    ImageView ivMenuPelangganKembali, ivMenuPelangganAddPelanggan;

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
        dataPelanggan = new ArrayList<>();
        dataPelanggan.clear();

        dataPelanggan.add(0, new PelangganModel("1", "1", "1", "Aceh", "Nanggroe Aceh Darussalam", "Tulang Bawang", "Dadang Sudadang", "dadang@gmail.com", "+6285389807650", "+6287889094567"));
        dataPelanggan.add(1, new PelangganModel("2", "1", "1", "Sumatera Utara", "Medan", "Deli Serdang", "Izharuddin Malik Ibrahim", "iz.malik1997@gmail.com", "+6285389807650", "+6287889094567"));
        dataPelanggan.add(2, new PelangganModel("3", "1", "1", "Sumatera Barat", "Padang", "Padang Sidempuan", "Muh. Ardian", "muh.ardian@gmail.com", "+6285389807650", "+6287889094567"));
        dataPelanggan.add(3, new PelangganModel("4", "1", "1", "Sumatera Selatan", "Palembang", "Sriwijaya", "Yustinus Prast", "yustinusprast@gmail.com", "+6285389807650", "+6287889094567"));
        dataPelanggan.add(4, new PelangganModel("5", "1", "1", "Lampung", "Lampung", "Lampung Selatan", "Nanda", "ec.nanda@gmail.com", "+6285389807650", "+6287889094567"));
        dataPelanggan.add(5, new PelangganModel("6", "1", "1", "Riau", "Pekanbaru", "Pekanbaru Madya", "Galih", "sarwito.salim@gmail.com", "+6285389807650", "+6287889094567"));

        adapter = new AdapterPilihPelanggan(this, dataPelanggan);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recPelanggan.setLayoutManager(mLayoutManager);
        recPelanggan.setItemAnimator(new DefaultItemAnimator());
        recPelanggan.setItemViewCacheSize(dataPelanggan.size());
        recPelanggan.setDrawingCacheEnabled(true);
        recPelanggan.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recPelanggan.setAdapter(adapter);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.animation_slide_from_right);
        recPelanggan.setLayoutAnimation(animation);
        adapter.notifyDataSetChanged();
    }
}
