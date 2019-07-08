package com.ultra.pos.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ultra.pos.R;
import com.ultra.pos.api.APIConnect;
import com.ultra.pos.api.APIUrl;
import com.ultra.pos.api.BaseApiInterface;
import com.ultra.pos.api.SharedPrefManager;
import com.ultra.pos.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextInputEditText tietEmail, tietPassword;
    TextView tvLupaKataSandi;
    APIConnect apiConnect;
    BaseApiInterface mApiInterface;
    Dialog dialogLoading;
    SharedPrefManager pref;
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
            loginRequest();
        });

        View toastsucces = getLayoutInflater().inflate(R.layout.toast_success, null);
        View toastinfo = getLayoutInflater().inflate(R.layout.toast_information, null);
        View toastfail = getLayoutInflater().inflate(R.layout.toast_failed, null);

        apiConnect = new APIConnect(LoginActivity.this, toastsucces, toastinfo, toastfail);

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

        pref = new SharedPrefManager(LoginActivity.this);
        if (pref.isLoggedIn()){
            startActivity(new Intent(this, Dashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    public void loginRequest(){

        String emailUser = tietEmail.getText().toString();
        String passwordUser = tietPassword.getText().toString();

        if (emailUser.isEmpty()){
            tietEmail.setError("Wajib Diisi!");
            tietEmail.requestFocus();
        }
        if (passwordUser.isEmpty()){
            tietPassword.setError("Wajib Diisi!");
            tietPassword.requestFocus();
        }

        apiConnect.showdialogloading();

        HashMap<String, String> params = new HashMap<>();
        params.put("email_user", emailUser);
        params.put("password", passwordUser);
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.postLogin( params,emailUser, passwordUser).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        String result = response.body().string();

                        JSONObject jsonResult = new JSONObject(result);
                        jsonResult.getString("success");
                        jsonResult.getString("message");

                        JSONArray jsonUser = jsonResult.getJSONArray("info");
                        for (int i = 0; i < jsonUser.length(); i++){
                            JSONObject objUser = jsonUser.getJSONObject(i);
                            UserModel userModel = new UserModel(
                                    objUser.getString("iduser"),
                                    objUser.getString("nama_user"),
                                    objUser.getString("telp_user"),
                                    objUser.getString("email_user"),
                                    objUser.getString("role_user"),
                                    objUser.getString("idbusiness"),
                                    objUser.getString("idoutlet"),
                                    objUser.getString("status_user"),
                                    objUser.getString("idtb"),
                                    objUser.getString("nama_business"),
                                    objUser.getString("alamat_business"),
                                    objUser.getString("name_outlet"),
                                    objUser.getString("alamat_outlet")
                            );

                            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                            LoginActivity.this.startActivity(intent);

                            SharedPrefManager s = new SharedPrefManager(LoginActivity.this);
                            s.createSession(userModel);

                            apiConnect.showtoastsucces(LoginActivity.this.getResources().getString(R.string.sukses));
                            finish();
                        }
                    } catch (IOException e){
                        dialogLoading.dismiss();
                        apiConnect.showtoastfailed(LoginActivity.this.getResources().getString(R.string.gagal));
                        e.printStackTrace();
                    } catch (JSONException e){
//                        dialogLoading.dismiss();
                        apiConnect.showtoastfailed(LoginActivity.this.getResources().getString(R.string.gagal));
                        e.printStackTrace();
                    }
                } else{
                    dialogLoading.dismiss();
                    Toast.makeText(LoginActivity.this, "Gagal Login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                apiConnect.showtoastfailed(LoginActivity.this.getResources().getString(R.string.cekkoneksidataanda));
                dialogLoading.dismiss();
            }
        });
    }
}
