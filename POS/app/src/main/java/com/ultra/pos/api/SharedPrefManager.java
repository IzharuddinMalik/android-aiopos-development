package com.ultra.pos.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ultra.pos.activity.LoginActivity;
import com.ultra.pos.model.KategoriModel;
import com.ultra.pos.model.ProdukModel;
import com.ultra.pos.model.UserModel;

import java.util.HashMap;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "aiopos";
    public static final String ID_USER = "keyIdUser";
    public static final String NAMA_USER = "keyNamaUser";
    public static final String TELP_USER = "keyTelpUser";
    public static final String EMAIL_USER = "KeyEmailUser";
    public static final String ROLE_USER = "keyRoleUser";
    public static final String ID_BUSINESS = "keyIdBusiness";
    public static final String ID_OUTLET = "keyIdOutlet";
    public static final String STATUS_USER = "keyStatusUser";
    public static final String ID_TB = "keyIdTb";
    private static final String IS_LOGIN = "isLogin";

    public static final String ID_KATEGORI = "keyIDKategori";
    public static final String NAMA_KATEGORI = "keyNamaKategori";
    public static final String ID_PRODUK = "keyIDProduk";
    public static final String NAMA_OUTLET = "keyNamaOutlet";
    public static final String NAMA_PRODUK = "keyNamaProduk";
    public static final String HARGA_PRODUK = "keyHargaProduk";
    public static final String GAMBAR_PRODUK = "keyGambarProduk";
    public static final String STATUS_PRODUK = "keyStatusProduk";
    public static final String VARIANT = "keyVariant";
    public static final String NAMA_VARIANT = "keyNamaVariant";

    private static SharedPrefManager mInstance;
    private static Context mCtx;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int mode = 0;

    public SharedPrefManager(Context context){
        mCtx = context;
        pref = context.getSharedPreferences(SHARED_PREF_NAME, mode);
        editor = pref.edit();
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void createSession(UserModel user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(ID_USER, user.getIdUser());
        editor.putString(NAMA_USER, user.getNamaUser());
        editor.putString(TELP_USER, user.getTelpUser());
        editor.putString(EMAIL_USER, user.getEmailUser());
        editor.putString(ROLE_USER, user.getRoleUser());
        editor.putString(ID_BUSINESS, user.getIdBusiness());
        editor.putString(ID_OUTLET, user.getIdOutlet());
        editor.putString(STATUS_USER, user.getStatusUser());
        editor.putString(ID_TB, user.getIdTb());
        editor.commit();
    }

    public void createSessionKategori(KategoriModel kategori){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID_KATEGORI, kategori.getIdKategori());
        editor.putString(NAMA_KATEGORI, kategori.getNamaKategori());
        editor.commit();
    }

    public void createSessionProduk(ProdukModel produk){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID_PRODUK, produk.getIdProduk());
        editor.putString(ID_BUSINESS, produk.getIdBusiness());
        editor.putString(ID_OUTLET, produk.getIdOutlet());
        editor.putString(NAMA_OUTLET, produk.getNamaOutlet());
        editor.putString(NAMA_PRODUK, produk.getNamaProduk());
        editor.putString(VARIANT, produk.getVariant());
        editor.putString(ID_KATEGORI, produk.getIdKategori());
        editor.putString(NAMA_VARIANT, produk.getNamaVariant());
        editor.putString(STATUS_PRODUK, produk.getStatusProduk());
        editor.putString(GAMBAR_PRODUK, produk.getGambarProduk());
        editor.putString(HARGA_PRODUK, produk.getHargaProduk());
        editor.commit();
    }

    public boolean isLoggedIn(){ return pref.getBoolean(IS_LOGIN, false);}

    public void logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(SHARED_PREF_NAME, pref.getString(SHARED_PREF_NAME, null));
        user.put(ID_USER, pref.getString(ID_USER, null));
        user.put(NAMA_USER, pref.getString(NAMA_USER, null));
        user.put(TELP_USER, pref.getString(TELP_USER, null));
        user.put(EMAIL_USER, pref.getString(EMAIL_USER, null));
        user.put(ROLE_USER, pref.getString(ROLE_USER, null));
        user.put(ID_BUSINESS, pref.getString(ID_BUSINESS, null));
        user.put(ID_OUTLET, pref.getString(ID_OUTLET, null));
        user.put(STATUS_USER, pref.getString(STATUS_USER, null));
        user.put(ID_TB, pref.getString(ID_TB, null));
        return user;
    }

    public HashMap<String, String> getKategoriDetails(){
        HashMap<String, String> kategori = new HashMap<>();
        kategori.put(SHARED_PREF_NAME, pref.getString(SHARED_PREF_NAME, null));
        kategori.put(ID_KATEGORI, pref.getString(ID_KATEGORI, null));
        kategori.put(NAMA_KATEGORI, pref.getString(NAMA_KATEGORI, null));
        return kategori;
    }

    public HashMap<String, String> getProdukDetails(){
        HashMap<String, String> produk = new HashMap<>();
        produk.put(SHARED_PREF_NAME, pref.getString(SHARED_PREF_NAME, null));
        produk.put(ID_PRODUK, pref.getString(ID_PRODUK, null));
        produk.put(ID_BUSINESS, pref.getString(ID_BUSINESS, null));
        produk.put(ID_OUTLET, pref.getString(ID_OUTLET, null));
        produk.put(NAMA_OUTLET, pref.getString(NAMA_OUTLET, null));
        produk.put(NAMA_PRODUK, pref.getString(NAMA_PRODUK, null));
        produk.put(VARIANT, pref.getString(VARIANT, null));
        produk.put(ID_KATEGORI, pref.getString(ID_KATEGORI, null));
        produk.put(NAMA_VARIANT, pref.getString(NAMA_VARIANT, null));
        produk.put(STATUS_PRODUK, pref.getString(STATUS_PRODUK, null));
        produk.put(GAMBAR_PRODUK, pref.getString(GAMBAR_PRODUK, null));
        produk.put(HARGA_PRODUK, pref.getString(HARGA_PRODUK, null));
        return produk;
    }
}
