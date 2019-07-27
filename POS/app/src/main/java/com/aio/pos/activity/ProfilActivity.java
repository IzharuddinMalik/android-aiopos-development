package com.aio.pos.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aio.pos.R;
import com.aio.pos.adapter.AdapterPilihProduk;
import com.aio.pos.api.SharedPrefManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.HashMap;

public class ProfilActivity extends AppCompatActivity {

    private SharedPrefManager pref;
    TextView namaBisnis,emailBisnis,Outlet,tipeAkun,alamatBisnis;
    ImageView ivLogoBisnis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

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

        namaBisnis= findViewById(R.id.tvMenuProfilNamaBisnis2);
        emailBisnis= findViewById(R.id.tvMenuProfilEmailAkun2);
        Outlet= findViewById(R.id.tvMenuProfilOutlet2);
        tipeAkun= findViewById(R.id.tvMenuProfilTipeAkun2);
        alamatBisnis= findViewById(R.id.tvMenuProfilAlamatBisnis2);
        ivLogoBisnis = findViewById(R.id.ivMenuProfilImageBisnis);

        pref = new SharedPrefManager(this);
        HashMap<String, String> user = pref.getUserDetails();
        String namabisnis = user.get(SharedPrefManager.NAMA_BISNIS);
        String emailbisnis = user.get(SharedPrefManager.EMAIL_USER);
        String outlet = user.get(SharedPrefManager.NAMA_OUTLET);
        String alamatbisnis = user.get(SharedPrefManager.ALAMAT_BISNIS);
        namaBisnis.setText(namabisnis);
        emailBisnis.setText(emailbisnis);
        Outlet.setText(outlet);
        tipeAkun.setText("Free");
        alamatBisnis.setText(alamatbisnis);
        Picasso.with(this).load("http://backoffice.aiopos.id/picture/" + user.get(SharedPrefManager.IMG_BUSINESS)).transform(new CircleTransform()).into(ivLogoBisnis);
    }

    class CircleTransform implements Transformation {

        boolean mCircleSeparator = false;

        public CircleTransform() {
        }

        public CircleTransform(boolean circleSeparator) {
            mCircleSeparator = circleSeparator;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
            Canvas canvas = new Canvas(bitmap);
            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);
            paint.setShader(shader);
            float r = size / 2f;
            canvas.drawCircle(r, r, r - 1, paint);
            // Make the thin border:
            Paint paintBorder = new Paint();
            paintBorder.setStyle(Paint.Style.STROKE);
            paintBorder.setColor(Color.argb(84,0,0,0));
            paintBorder.setAntiAlias(true);
            paintBorder.setStrokeWidth(1);
            canvas.drawCircle(r, r, r-1, paintBorder);

            // Optional separator for stacking:
            if (mCircleSeparator) {
                Paint paintBorderSeparator = new Paint();
                paintBorderSeparator.setStyle(Paint.Style.STROKE);
                paintBorderSeparator.setColor(Color.parseColor("#FFFFFF"));
                paintBorderSeparator.setAntiAlias(true);
                paintBorderSeparator.setStrokeWidth(4);
                canvas.drawCircle(r, r, r+1, paintBorderSeparator);
            }
            squaredBitmap.recycle();
            return bitmap;
        }


        @Override
        public String key() {
            return "circle";
        }
    }

    public void back(View view){
        startActivity(new Intent(this, PengaturanActivity.class));
        finish();
    }
}
