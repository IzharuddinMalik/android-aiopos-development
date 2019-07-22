package com.aio.pos.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aio.pos.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LupaKataSandiActivity extends AppCompatActivity {

    TextView tvMasukLogin;
    Button btnLupaPassword;
    private FirebaseAuth auth;
    EditText edtEmailForget;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_kata_sandi);

        tvMasukLogin = findViewById(R.id.tvMasukLupaKataSandi);

        tvMasukLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        btnLupaPassword = findViewById(R.id.btnKirimLupaPassword);

        btnLupaPassword.setOnClickListener(view -> {
            sendEmailPassword();
        });

        edtEmailForget = findViewById(R.id.edtEmailYangTerdaftar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

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
    }

    public void sendEmailPassword(){
        String email = edtEmailForget.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            edtEmailForget.setError("Masukkan Email Anda");
            edtEmailForget.requestFocus();
        }

        auth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LupaKataSandiActivity.this, "Kita telah mengirim ulang reset password ke email anda", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LupaKataSandiActivity.this, "Gagal mengirim reset password ke email anda", Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        });
    }
}
