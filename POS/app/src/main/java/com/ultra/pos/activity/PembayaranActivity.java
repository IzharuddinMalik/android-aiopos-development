package com.ultra.pos.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Button;

import com.ultra.pos.R;
import com.ultra.pos.adapter.AdapterRingkasanOrder;
import com.ultra.pos.adapter.AdapterTransaksiTersimpan;
import com.ultra.pos.model.OrderModel;
import com.ultra.pos.model.TransaksiModel;

import java.util.ArrayList;
import java.util.List;

public class PembayaranActivity extends AppCompatActivity {


    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    Button konfirmasibayar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        konfirmasibayar=findViewById(R.id.btnBayarPembayaran);

        konfirmasibayar.setOnClickListener(v -> {
            startActivity(new Intent(this,PembayaranSuksesActivity.class));
            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu_pembayaran,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.catatan){
            dialogMenuCatatan();
        }
        return true;
    }

    public void dialogMenuCatatan(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_catatan,null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        dialog.show();
    }
}
