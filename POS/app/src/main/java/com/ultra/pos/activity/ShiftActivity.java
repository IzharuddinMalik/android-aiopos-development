package com.ultra.pos.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ultra.pos.R;

public class ShiftActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    int backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);
        Toolbar toolbar = findViewById(R.id.shift_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_shift_layout);
        NavigationView navigationView = findViewById(R.id.nav_shift_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void pilihan_shift(View view){
        startActivity(new Intent(this, PilihanShiftActivity.class));
        finish();
    }
    public void shift_saat_ini(View view){
        startActivity(new Intent(this, ShiftSaatIniActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        backpress = (backpress + 1);
        Toast.makeText(this, "Tekan Tombol Sekali Lagi", Toast.LENGTH_SHORT).show();

        if (backpress > 1){
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_transaksi) {
            startActivity(new Intent(this, Dashboard.class));
            finish();
        } else if (id == R.id.nav_transhistory) {

        } else if (id == R.id.nav_shiftkerja) {
            startActivity(new Intent(this, ShiftActivity.class));
            finish();
        } else if (id == R.id.nav_pengaturan) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_shift_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
