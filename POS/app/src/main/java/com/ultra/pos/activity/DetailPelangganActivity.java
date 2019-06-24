package com.ultra.pos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ultra.pos.R;

public class DetailPelangganActivity extends AppCompatActivity {

    TextView tvDetailNamaPelanggan, tvDetailNoTelpPelanggan, tvDetailEmailPelanggan, tvDetailNamaProvPelanggan, tvDetailNamaKecPelanggan,
            tvDetailNamaKabKotPelanggan;
    ImageView ivDetailBtnEditPelanggan, ivDetailBtnKembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pelanggan);

        tvDetailNamaPelanggan = findViewById(R.id.tvMenuDetailPelangganNamaPelanggan);
        tvDetailNoTelpPelanggan = findViewById(R.id.tvMenuDetailPelangganNoTelpPelanggan);
        tvDetailEmailPelanggan = findViewById(R.id.tvMenuDetailPelangganEmailPelanggan);
        tvDetailNamaProvPelanggan = findViewById(R.id.tvMenuDetailPelangganNamaProvinsiMember);
        tvDetailNamaKabKotPelanggan = findViewById(R.id.tvMenuDetailPelangganNamaKabKotMember);
        tvDetailNamaKecPelanggan = findViewById(R.id.tvMenuDetailPelangganNamaKecamatanMember);
        ivDetailBtnEditPelanggan = findViewById(R.id.ivMenuDetailPelangganEditPelanggan);
        ivDetailBtnKembali = findViewById(R.id.ivMenuDetailPelangganArrowBack);

        tvDetailNamaPelanggan.setText(getIntent().getStringExtra("namaPelanggan"));
        tvDetailNoTelpPelanggan.setText(getIntent().getStringExtra("noHp"));
        tvDetailEmailPelanggan.setText(getIntent().getStringExtra("emailPelanggan"));
        tvDetailNamaProvPelanggan.setText(getIntent().getStringExtra("namaProvinsi"));
        tvDetailNamaKabKotPelanggan.setText(getIntent().getStringExtra("namaKabKot"));
        tvDetailNamaKecPelanggan.setText(getIntent().getStringExtra("namaKecamatan"));
        ivDetailBtnEditPelanggan.setOnClickListener(v -> {
            startActivity(new Intent(this, EditPelangganActivity.class));
        });
        ivDetailBtnKembali.setOnClickListener(v -> {
            startActivity(new Intent(this, PelangganActivity.class));
        });
    }

    public void onBackPressed(){
        startActivity(new Intent(this, PelangganActivity.class));
    }
}
