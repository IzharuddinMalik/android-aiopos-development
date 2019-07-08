package com.ultra.pos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ultra.pos.R;

public class DetailShiftActivity extends AppCompatActivity {

    TextView data1,data2,tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_shift);

        data1=findViewById(R.id.tvData1);
        data2=findViewById(R.id.tvData2);
        tanggal=findViewById(R.id.tvTanggal);

        data1.setText(getIntent().getStringExtra("data1"));
        data2.setText(getIntent().getStringExtra("data2"));
        tanggal.setText(getIntent().getStringExtra("tanggal"));
    }

    public void back(View view){
        startActivity(new Intent(this, ShiftHistoryActivity.class));
        finish();
    }
}
