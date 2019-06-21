package com.ultra.pos.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.ultra.pos.R;

import java.util.List;

public class AddPelangganActivity extends AppCompatActivity {

    AutoCompleteTextView actvProvinsiPelanggan, actvKabupatenPelanggan, actvKecamatanPelanggan;
    String[] provinsi = {"Aceh", "Sumatera Utara", "Sumatera Barat", "Sumatera Selatan", "Lampung", "Riau", "Banten", "DKI Jakarta",
            "Jawa Barat", "DI Yogyakarta", "Jawa Tengah", "Jawa Timur"};
    String[] kabupaten = {"Nanggroe Aceh Darussalam", "Medan", "Padang", "Palembang", "Lampung", "Pekanbaru", "Tangerang", "Jakarta Pusat",
            "Bandung", "Kota Yogyakarta", "Semarang", "Surabaya"};
    String[] kecamatan = {"Tulungagung", "Prambanan", "Kalasan", "Berbah", "Ngaglik", "UmbulHarjo", "Depok", "Maguwoharjo", "Turi"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pelanggan);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, provinsi);
        actvProvinsiPelanggan = findViewById(R.id.actvMenuTambahPelangganProvinsiPelanggan);
        actvProvinsiPelanggan.setThreshold(1);
        actvProvinsiPelanggan.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, kabupaten);
        actvKabupatenPelanggan = findViewById(R.id.actvMenuTambahPelangganKabupatenPelanggan);
        actvKabupatenPelanggan.setThreshold(1);
        actvKabupatenPelanggan.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, kecamatan);
        actvKecamatanPelanggan = findViewById(R.id.actvMenuTambahPelangganKecamatanPelanggan);
        actvKecamatanPelanggan.setThreshold(1);
        actvKecamatanPelanggan.setAdapter(adapter2);
    }
}
