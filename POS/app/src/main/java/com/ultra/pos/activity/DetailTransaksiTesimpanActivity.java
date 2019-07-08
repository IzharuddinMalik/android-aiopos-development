package com.ultra.pos.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

import com.ultra.pos.R;
import com.ultra.pos.adapter.AdapterPesanan;
import com.ultra.pos.model.Produk;

import java.util.ArrayList;
import java.util.List;

public class DetailTransaksiTesimpanActivity extends AppCompatActivity {

    TextView total,jam;
    Button lanjut,cetak;
    RecyclerView recPesanan;
    private List<Produk> listPesanan;
    private AdapterPesanan adapter;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi_tesimpan);

        total = findViewById(R.id.tvTotalBayarTransaksi);
        jam = findViewById(R.id.tvJamTransaksi);

        total.setText(getIntent().getStringExtra("totalharga"));
        jam.setText(getIntent().getStringExtra("jamtransaksi"));


        recPesanan = findViewById(R.id.rvTransaksiTersimpan);
        listPesanan = new ArrayList<>();
        listPesanan.clear();

        listPesanan.add(0, new Produk("1", "Nasi", "1", "Aceh","3000","3","1"));
        listPesanan.add(1, new Produk("2", "Teh", "1", "Aceh","2000","3","1"));
        listPesanan.add(2, new Produk("3", "Ayam", "1", "Aceh","6000","3","1"));
        listPesanan.add(3, new Produk("4", "Gorengan", "1", "Aceh","1000","7","1"));

        adapter = new AdapterPesanan(this, listPesanan);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recPesanan.setLayoutManager(mLayoutManager);
        recPesanan.setItemAnimator(new DefaultItemAnimator());
        recPesanan.setItemViewCacheSize(listPesanan.size());
        recPesanan.setDrawingCacheEnabled(true);
        recPesanan.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recPesanan.setAdapter(adapter);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.animation_slide_from_right);
        recPesanan.setLayoutAnimation(animation);
        adapter.notifyDataSetChanged();


        lanjut=findViewById(R.id.btnLanjutOrderTransaksi);

        lanjut.setOnClickListener(v -> {
            startActivity(new Intent(this, RingkasanOrderActivity.class));
        });

        cetak=findViewById(R.id.btnCetakStrukTransaksi);

        cetak.setOnClickListener(v -> {
            dialogPrint();
        });
    }

    public void dialogPrint(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_cetak,null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        dialog.show();
    }
}
