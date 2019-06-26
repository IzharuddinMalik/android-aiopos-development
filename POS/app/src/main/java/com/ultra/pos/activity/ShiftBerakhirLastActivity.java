package com.ultra.pos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ultra.pos.R;

public class ShiftBerakhirLastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_berakhir_last);
    }

    public void akhiri(View view){
        startActivity(new Intent(this, ShiftOtomatisActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
