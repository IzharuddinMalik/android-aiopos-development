package com.ultra.pos.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.Button;
import android.widget.TextView;

import com.ultra.pos.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextInputEditText tietEmail, tietPassword;
    TextView tvLupaKataSandi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        tietEmail = findViewById(R.id.tietEmailLogin);
        tietPassword = findViewById(R.id.tietPasswordLogin);
        tvLupaKataSandi = findViewById(R.id.tvLupaKataSandi);

        tvLupaKataSandi.setOnClickListener(v -> {
            startActivity(new Intent(this, LupaKataSandiActivity.class));
        });

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, Dashboard.class));
            finish();
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", "" + width);
        Log.d("height", "" + height);

        if (width >= 1920 && height >= 1200){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
