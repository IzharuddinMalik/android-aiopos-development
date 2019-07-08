package com.ultra.pos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.ultra.pos.R;

public class PembayaranSuksesActivity extends AppCompatActivity {

    Button bayarselesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran_sukses);

        bayarselesai=findViewById(R.id.btnSelesaiPembayaran);

        bayarselesai.setOnClickListener(v -> {
            startActivity(new Intent(this,Dashboard.class));
            finish();
        });
    }


}
