package com.aio.pos.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

import com.aio.pos.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }, 3000L);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", "" + width);
        Log.d("height", "" + height);

        if ((width % 9)==0 && (height % 16)==0){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

    }
}
