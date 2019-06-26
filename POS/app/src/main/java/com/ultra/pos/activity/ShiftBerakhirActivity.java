package com.ultra.pos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ultra.pos.R;

public class ShiftBerakhirActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_berakhir);
    }

    public void konfirmasi(View view){
        startActivity(new Intent(this, ShiftBerakhirLastActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
