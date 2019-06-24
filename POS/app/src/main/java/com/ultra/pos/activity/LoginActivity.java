package com.ultra.pos.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }
}
