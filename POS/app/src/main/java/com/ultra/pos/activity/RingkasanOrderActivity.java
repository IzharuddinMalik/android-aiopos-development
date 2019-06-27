package com.ultra.pos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.ultra.pos.R;
import com.ultra.pos.adapter.AdapterRingkasanOrder;
import com.ultra.pos.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class RingkasanOrderActivity extends AppCompatActivity {
    RecyclerView recOrder;
    private List<OrderModel> listOrder;
    private AdapterRingkasanOrder adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringkasan_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recOrder = findViewById(R.id.rvRingkasanOrder);
        listOrder = new ArrayList<>();
        listOrder.clear();

        listOrder.add(0, new OrderModel("Tanggal 25 Feb", "1", "1"));
        listOrder.add(1, new OrderModel("Tanggal 25 Feb", "1", "1"));
        listOrder.add(2, new OrderModel("Tanggal 25 Feb", "1", "1"));
        listOrder.add(3, new OrderModel("Tanggal 25 Feb", "1", "1"));
        listOrder.add(4, new OrderModel("Tanggal 25 Feb", "1", "1"));
        listOrder.add(5, new OrderModel("Tanggal 25 Feb", "1", "1"));
        listOrder.add(6, new OrderModel("Tanggal 25 Feb", "1", "1"));

        adapter = new AdapterRingkasanOrder(this, listOrder);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recOrder.setLayoutManager(mLayoutManager);
        recOrder.setItemAnimator(new DefaultItemAnimator());
        recOrder.setItemViewCacheSize(listOrder.size());
        recOrder.setDrawingCacheEnabled(true);
        recOrder.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recOrder.setAdapter(adapter);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.animation_slide_from_right);
        recOrder.setLayoutAnimation(animation);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    public void back(View view){
        startActivity(new Intent(this, PengaturanActivity.class));
        finish();
    }
}
