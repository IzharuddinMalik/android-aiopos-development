package com.ultra.pos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ultra.pos.R;

public class TransaksiTersimpanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_tersimpan);
    }

    public void back(View view){
        startActivity(new Intent(this, Dashboard.class));
        finish();
    }
}
