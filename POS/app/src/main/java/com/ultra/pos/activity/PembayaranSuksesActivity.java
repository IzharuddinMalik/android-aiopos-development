package com.ultra.pos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.ultra.pos.R;

public class PembayaranSuksesActivity extends AppCompatActivity {

    Button bayarselesai;
    TextView KembalianPembayaran;
    String kembalian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran_sukses);

        bayarselesai=findViewById(R.id.btnSelesaiPembayaran);
        KembalianPembayaran=findViewById(R.id.tvKembalianPembayaran);
        kembalian = getIntent().getStringExtra("kembalian");
        KembalianPembayaran.setText("Rp. "+kembalian);


        bayarselesai.setOnClickListener(v -> {
            startActivity(new Intent(this,Dashboard.class));
            finish();
        });
    }


}
