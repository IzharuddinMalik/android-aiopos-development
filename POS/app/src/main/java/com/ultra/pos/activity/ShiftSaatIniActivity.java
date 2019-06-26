package com.ultra.pos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ultra.pos.R;

public class ShiftSaatIniActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_saat_ini);
    }

    public void akhiri(View view){
        startActivity(new Intent(this, ShiftBerakhirActivity.class));
        finish();
    }

    public void back(View view){
        startActivity(new Intent(this, ShiftActivity.class));
    }
}
