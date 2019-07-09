package com.ultra.pos.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

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
    TextView total;
    int totalKembalian=0;
    TextInputEditText jumlahLain;
    String totalbayar;
    Button konfirmasibayar,pas,limapuluh,seratus,duaratus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        konfirmasibayar=findViewById(R.id.btnBayarPembayaran);
        pas=findViewById(R.id.btnUangPas);
        limapuluh=findViewById(R.id.btnUang50K);
        seratus=findViewById(R.id.btnUang100K);
        duaratus=findViewById(R.id.btnUang200K);
        total=findViewById(R.id.tvTotalPembayaranAct);
        jumlahLain=findViewById(R.id.tietJumlahLain);

        totalbayar = getIntent().getStringExtra("totalbyr");

        total.setText("Rp. "+totalbayar);

        konfirmasibayar.setOnClickListener(v -> {
            Intent intent = new Intent(this, PembayaranSuksesActivity.class);
            if(jumlahLain.getText().toString().equals("0")){
                intent.putExtra("kembalian", String.valueOf(totalKembalian));
                Log.i("Bukan Jml lain",""+totalKembalian);
            }else{
                totalKembalian=Integer.parseInt(jumlahLain.getText().toString())-Integer.parseInt(totalbayar);
                intent.putExtra("kembalian", String.valueOf(totalKembalian));
                Log.i("Jml Lain",""+totalKembalian);
            }

            startActivity(intent);
        });

        pas.setOnClickListener(v -> {
            totalKembalian=0;
            Log.i("Pas",""+totalKembalian);
        });

        limapuluh.setOnClickListener(v -> {
            totalKembalian=50000-Integer.parseInt(totalbayar);
            Log.i("Sisa 50",""+totalKembalian);
        });

        seratus.setOnClickListener(v -> {
            totalKembalian=100000-Integer.parseInt(totalbayar);
            Log.i("Sisa 100",""+totalKembalian);
        });

        duaratus.setOnClickListener(v -> {
            totalKembalian=200000-Integer.parseInt(totalbayar);
            Log.i("Sisa 200",""+totalKembalian);
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
