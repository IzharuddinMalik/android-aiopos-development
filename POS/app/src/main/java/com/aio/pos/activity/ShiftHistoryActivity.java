package com.aio.pos.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.aio.pos.R;
import com.aio.pos.adapter.AdapterShiftHistory;
import com.aio.pos.model.HistoryModel;

import java.util.ArrayList;
import java.util.List;

public class ShiftHistoryActivity extends AppCompatActivity {

    RecyclerView recHistory;
    private List<HistoryModel> listHistory;
    private AdapterShiftHistory adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_history);

        recHistory = findViewById(R.id.rvShiftHistory);
        listHistory = new ArrayList<>();
        listHistory.clear();

        listHistory.add(0, new HistoryModel("Tanggal 25 Feb", "1", "1"));
        listHistory.add(1, new HistoryModel("Tanggal 25 Feb", "1", "1"));
        listHistory.add(2, new HistoryModel("Tanggal 25 Feb", "1", "1"));
        listHistory.add(3, new HistoryModel("Tanggal 25 Feb", "1", "1"));

        adapter = new AdapterShiftHistory(this, listHistory);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recHistory.setLayoutManager(mLayoutManager);
        recHistory.setItemAnimator(new DefaultItemAnimator());
        recHistory.setItemViewCacheSize(listHistory.size());
        recHistory.setDrawingCacheEnabled(true);
        recHistory.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recHistory.setAdapter(adapter);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.animation_slide_from_right);
        recHistory.setLayoutAnimation(animation);
        adapter.notifyDataSetChanged();

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

    public void back(View view){
        startActivity(new Intent(this, ShiftActivity.class));
        finish();
    }
}
