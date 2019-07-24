package com.aio.pos.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aio.pos.R;
import com.aio.pos.api.APIConnect;
import com.aio.pos.api.APIUrl;
import com.aio.pos.api.BaseApiInterface;
import com.aio.pos.api.SharedPrefManager;
import com.aio.pos.model.UserModel;


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

    Button btnLogin, btnDaftar;
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
        btnDaftar = findViewById(R.id.btnRegister);

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
        Log.i("Tes", "iki" + getResources().getConfiguration().smallestScreenWidthDp);

        if (getResources().getConfiguration().smallestScreenWidthDp == 360){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            btnDaftar.setOnClickListener(view -> {
                Uri uri = Uri.parse("https://backoffice.aiopos.id/home/register");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            });
        } else {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        pref = new SharedPrefManager(LoginActivity.this);
        if (pref.isLoggedIn()){
            startActivity(new Intent(this, Dashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        dialogLoading = new Dialog(LoginActivity.this);

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

                        dialogLoading.dismiss();
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
                                    objUser.getString("img_business"),
                                    objUser.getString("idtb"),
                                    objUser.getString("nama_business"),
                                    objUser.getString("alamat_business"),
                                    objUser.getString("name_outlet"),
                                    objUser.getString("alamat_outlet"),
                                    objUser.getString("idtax"),
                                    objUser.getString("nama_tax"),
                                    objUser.getString("besaran_tax")
                            );

                            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                            LoginActivity.this.startActivity(intent);

                            SharedPrefManager s = new SharedPrefManager(LoginActivity.this);
                            s.createSession(userModel);

                            apiConnect.showtoastsucces(LoginActivity.this.getResources().getString(R.string.sukses));
                            finish();
                        }
                    } catch (JSONException e){
                        apiConnect.closeDialogloading();
                        apiConnect.showtoastfailed(LoginActivity.this.getResources().getString(R.string.GagalloginBukanakunkaryawan));
                        e.printStackTrace();
                    } catch (IOException e){
                        apiConnect.showtoastfailed(LoginActivity.this.getResources().getString(R.string.gagal));
                        apiConnect.closeDialogloading();
                        e.printStackTrace();
                    }
                } else{
                    apiConnect.closeDialogloading();
                    apiConnect.showtoastfailed(LoginActivity.this.getResources().getString(R.string.gagal));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                apiConnect.showtoastfailed(LoginActivity.this.getResources().getString(R.string.cekkoneksidataanda));
                apiConnect.closeDialogloading();
            }
        });
    }
}
