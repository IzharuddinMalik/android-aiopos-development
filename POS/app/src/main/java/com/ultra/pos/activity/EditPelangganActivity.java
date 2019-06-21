package com.ultra.pos.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.ultra.pos.R;

public class EditPelangganActivity extends AppCompatActivity {

    AutoCompleteTextView actvEditProvinsiPelanggan, actvEditKabupatenPelanggan, actvEditKecamatanPelanggan;
    String[] provinsi = {"Aceh", "Sumatera Utara", "Sumatera Barat", "Sumatera Selatan", "Lampung", "Riau", "Banten", "DKI Jakarta",
            "Jawa Barat", "DI Yogyakarta", "Jawa Tengah", "Jawa Timur"};
    String[] kabupaten = {"Nanggroe Aceh Darussalam", "Medan", "Padang", "Palembang", "Lampung", "Pekanbaru", "Tangerang", "Jakarta Pusat",
            "Bandung", "Kota Yogyakarta", "Semarang", "Surabaya"};
    String[] kecamatan = {"Tulungagung", "Prambanan", "Kalasan", "Berbah", "Ngaglik", "UmbulHarjo", "Depok", "Maguwoharjo", "Turi"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pelanggan);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, provinsi);
        actvEditProvinsiPelanggan = findViewById(R.id.actvMenuEditPelangganProvinsiPelanggan);
        actvEditProvinsiPelanggan.setThreshold(1);
        actvEditProvinsiPelanggan.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, kabupaten);
        actvEditKabupatenPelanggan = findViewById(R.id.actvMenuEditPelangganKabupatenPelanggan);
        actvEditKabupatenPelanggan.setThreshold(1);
        actvEditKabupatenPelanggan.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, kecamatan);
        actvEditKecamatanPelanggan = findViewById(R.id.actvMenuEditPelangganKecamatanPelanggan);
        actvEditKecamatanPelanggan.setThreshold(1);
        actvEditKecamatanPelanggan.setAdapter(adapter2);
    }
}
