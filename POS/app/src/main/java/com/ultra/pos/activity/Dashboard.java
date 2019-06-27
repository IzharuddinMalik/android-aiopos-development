package com.ultra.pos.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.ultra.pos.R;
import com.ultra.pos.adapter.TabAdapter;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout tabLayout;
    TabAdapter adapter;
    ViewPager viewPager;
    ImageView ivKeranjang, ivOptionMenu, ivSearch;
    LinearLayout llDashboardBukaPelanggan;
    SearchView svNamaProduk;
    int backpress;
    Boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorGray4d4d4d));
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = findViewById(R.id.frameLayout);
        tabLayout = findViewById(R.id.tabs);
        ivKeranjang = findViewById(R.id.ivDashboardKeranjang);

        adapter = new TabAdapter(getSupportFragmentManager());

        adapter.addFragment(new DashboardFragment(), "Favorit");
        adapter.addFragment(new DashboardFragment(), "Makanan");
        adapter.addFragment(new DashboardFragment(), "Minuman");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        if (tabLayout.getTabCount() == 2){
            tabLayout.setTabMode(tabLayout.MODE_FIXED);
        }else{
            tabLayout.setTabMode(tabLayout.MODE_SCROLLABLE);
        }

        ivSearch = findViewById(R.id.ivDashboardGambarSearch);
        svNamaProduk = findViewById(R.id.svDashboardNamaProduk);



        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", "" + width);
        Log.d("height", "" + height);

        if (width == 720 && height == 1280){

        }else{
            ivSearch.setOnClickListener(v -> {
                while (status == false){
                    svNamaProduk.setVisibility(View.GONE);
                }
            });

            ivOptionMenu = findViewById(R.id.ivDashboardMenuOptions);
            ivOptionMenu.setOnClickListener(this::onClickRight);
        }

        llDashboardBukaPelanggan = findViewById(R.id.llDashboardBukaPelanggan);
        llDashboardBukaPelanggan.setOnClickListener(v -> {
            startActivity(new Intent(this, PelangganActivity.class));
        });

    }

    private void onClickRight(View view) {
        setContentView(R.layout.activity_dashboard_right_button);
        DrawerLayout drawerRight = findViewById(R.id.drawer_layout_right_button);
        NavigationView navigationView1 = findViewById(R.id.nav_view_right_button);
        ActionBarDrawerToggle toggle1 = new ActionBarDrawerToggle(this, drawerRight, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerRight.addDrawerListener(toggle1);
        toggle1.syncState();
        navigationView1.setNavigationItemSelectedListener(this::onNavigationItemSelected1);
    }

    @Override
    public void onBackPressed() {
        backpress = (backpress + 1);
        Toast.makeText(this, "Tekan Tombol Sekali Lagi", Toast.LENGTH_SHORT).show();

        if (backpress > 1){
            finish();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_transaksi) {
            startActivity(new Intent(this, Dashboard.class));
            finish();
        } else if (id == R.id.nav_transhistory) {
            startActivity(new Intent(this,RingkasanOrderActivity.class));
            finish();
        } else if (id == R.id.nav_shiftkerja) {
            startActivity(new Intent(this, ShiftActivity.class));
            finish();
        } else if (id == R.id.nav_pengaturan) {
            startActivity(new Intent(this, PengaturanActivity.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean onNavigationItemSelected1(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.nav_diskon){

        } else if (id == R.id.nav_nama){

        } else if (id == R.id.nav_batalkanTransaksi){

        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_right_button);
        drawerLayout.closeDrawer(Gravity.END);
        return true;
    }
}
