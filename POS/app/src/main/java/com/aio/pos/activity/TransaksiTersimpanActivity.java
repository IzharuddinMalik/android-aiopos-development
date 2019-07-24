package com.aio.pos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.aio.pos.R;
import com.aio.pos.adapter.AdapterTransaksiTersimpan;
import com.aio.pos.model.TransaksiModel;

import java.util.ArrayList;
import java.util.List;

public class TransaksiTersimpanActivity extends AppCompatActivity {

    private List<TransaksiModel> listTransaksi;
    private AdapterTransaksiTersimpan adapter;
    RecyclerView recTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_tersimpan);

        recTransaksi = findViewById(R.id.rvTransaksiTersimpan);
        listTransaksi = new ArrayList<>();
        listTransaksi.clear();

        listTransaksi.add(0, new TransaksiModel("15000", "2", "", "14.20","",""));
        listTransaksi.add(1, new TransaksiModel("15000", "1", "", "10.00","",""));
        listTransaksi.add(2, new TransaksiModel("15000", "3", "", "13.30","",""));

        adapter = new AdapterTransaksiTersimpan(this,listTransaksi);
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

    public void back(View view){
        startActivity(new Intent(this, Dashboard.class));
        finish();
    }
}
