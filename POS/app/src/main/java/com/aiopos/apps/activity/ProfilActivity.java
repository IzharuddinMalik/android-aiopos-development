package com.aiopos.apps.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.aiopos.apps.R;
import com.aiopos.apps.api.SharedPrefManager;

import java.util.HashMap;

public class ProfilActivity extends AppCompatActivity {

    private SharedPrefManager pref;
    TextView namaBisnis,emailBisnis,Outlet,tipeAkun,alamatBisnis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", "" + width);
        Log.d("height", "" + height);

        if (getResources().getConfiguration().smallestScreenWidthDp == 360){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        namaBisnis= findViewById(R.id.tvMenuProfilNamaBisnis);
        emailBisnis= findViewById(R.id.tvMenuProfilEmailAkun);
        Outlet= findViewById(R.id.tvMenuProfilOutlet);
        tipeAkun= findViewById(R.id.tvMenuProfilTipeAkun);
        alamatBisnis= findViewById(R.id.tvMenuProfilAlamatBisnis);

        pref = new SharedPrefManager(this);
        HashMap<String, String> user = pref.getUserDetails();
        String namabisnis = user.get(SharedPrefManager.NAMA_BISNIS);
        String emailbisnis = user.get(SharedPrefManager.EMAIL_USER);
        String outlet = user.get(SharedPrefManager.NAMA_OUTLET);
        String alamatbisnis = user.get(SharedPrefManager.ALAMAT_BISNIS);
        namaBisnis.setText(namabisnis);
        emailBisnis.setText(emailbisnis);
        Outlet.setText(outlet);
        tipeAkun.setText("Coming Soon");
        alamatBisnis.setText(alamatbisnis);
    }

    public void back(View view){
        startActivity(new Intent(this, PengaturanActivity.class));
        finish();
    }
}
