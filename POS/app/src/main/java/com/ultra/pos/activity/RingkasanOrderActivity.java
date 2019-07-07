package com.ultra.pos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.ultra.pos.R;
import com.ultra.pos.adapter.AdapterRingkasanOrder;
import com.ultra.pos.adapter.AdapterTipePenjualan;
import com.ultra.pos.model.OrderModel;
import com.ultra.pos.model.TipeModel;

import java.util.ArrayList;
import java.util.List;

public class RingkasanOrderActivity extends AppCompatActivity {
    RecyclerView recOrder,recTipe;
    Button simpan,bayar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    ImageView ivKeranjang, ivOptionMenu, ivSearch, ivCancelDialogDiskon, ivCancelDialogNama;
    Button btnSimpanDialogDiskon, btnSimpanDialogNama;
    private List<OrderModel> listOrder;
    private List<TipeModel> listTipe;
    private AdapterRingkasanOrder adapter;
    private AdapterTipePenjualan adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringkasan_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recOrder = findViewById(R.id.rvRingkasanOrder);
        simpan=findViewById(R.id.btnSimpanOrder);
        bayar=findViewById(R.id.btnBayarOrder);
        listOrder = new ArrayList<>();
        listOrder.clear();

        listOrder.add(0, new OrderModel("Nasi Goreng", "2", "15.000"));
        listOrder.add(1, new OrderModel("Ayam Goreng", "1", "18.000"));
        listOrder.add(2, new OrderModel("Es Teh", "3", "2000"));

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

        recTipe = findViewById(R.id.rvTipePenjualan);
        listTipe = new ArrayList<>();
        listTipe.clear();

        listTipe.add(0, new TipeModel("Go Food"));
        listTipe.add(1, new TipeModel("Dine In"));
        listTipe.add(2, new TipeModel("Take Away"));

        adapter2 = new AdapterTipePenjualan(this, listTipe);
        final RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        recTipe.setLayoutManager(mLayoutManager2);
        recTipe.setItemAnimator(new DefaultItemAnimator());
        recTipe.setItemViewCacheSize(listTipe.size());
        recTipe.setDrawingCacheEnabled(true);
        recTipe.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recTipe.setAdapter(adapter2);
        LayoutAnimationController animation2 = AnimationUtils.loadLayoutAnimation(this, R.anim.animation_slide_from_right);
        recTipe.setLayoutAnimation(animation2);
        adapter.notifyDataSetChanged();

        simpan.setOnClickListener(v -> {
            startActivity(new Intent(this,TransaksiTersimpanActivity.class));
            finish();
        });

        bayar.setOnClickListener(v -> {
            startActivity(new Intent(this,PembayaranActivity.class));
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.diskon){
            dialogMenuDiskon();
        } else if (item.getItemId() == R.id.hapus_pesanan) {
            dialogMenuHapus();
        }
        return true;
    }

    public void dialogMenuDiskon(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_diskon,null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        ivCancelDialogDiskon = dialogView.findViewById(R.id.ivCancelDialogDiskon);
        ivCancelDialogDiskon.setOnClickListener(v -> {

        });

        btnSimpanDialogDiskon = dialogView.findViewById(R.id.btnDialogFormSimpan);
        btnSimpanDialogDiskon.setOnClickListener(v -> {

        });

        dialog.show();
    }

    public void dialogMenuHapus(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_hapus,null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    public void back(View view){
        startActivity(new Intent(this, Dashboard.class));
        finish();
    }
}
