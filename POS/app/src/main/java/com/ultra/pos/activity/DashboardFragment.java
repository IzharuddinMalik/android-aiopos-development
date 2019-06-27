package com.ultra.pos.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;

import com.ultra.pos.R;
import com.ultra.pos.adapter.AdapterPilihProduk;
import com.ultra.pos.model.ProdukModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    View layout;
    RecyclerView recProduk;
    private List<ProdukModel> dataProduk;
    private AdapterPilihProduk adapter;
    public static final String API_Produk = "http://pos.ultrapreneur.id/produk_get_kategori.php";
    boolean status = false;
    LinearLayout linearLihatPesanan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        layout = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recProduk = layout.findViewById(R.id.rvDashboardListProduk);

        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        linearLihatPesanan = view.findViewById(R.id.llDashboardLihatPesanan);

        dataProduk = new ArrayList<>();
        dataProduk.clear();

        dataProduk.add(0, new ProdukModel("1", "Nasi Goreng", "Rp. 15.000", ""+R.drawable.logopos));
        dataProduk.add(1, new ProdukModel("2", "Nasi Goreng", "Rp. 15.000", ""+R.drawable.logopos));
        dataProduk.add(2, new ProdukModel("3", "Nasi Goreng", "Rp. 15.000", ""+R.drawable.logopos));
        dataProduk.add(3, new ProdukModel("4", "Nasi Goreng", "Rp. 15.000", ""+R.drawable.logopos));
        dataProduk.add(4, new ProdukModel("5", "Nasi Goreng", "Rp. 15.000", ""+R.drawable.logopos));

        adapter = new AdapterPilihProduk(getContext(), dataProduk);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recProduk.setLayoutManager(mLayoutManager);
        recProduk.setItemAnimator(new DefaultItemAnimator());
        recProduk.setItemViewCacheSize(dataProduk.size());
        recProduk.setDrawingCacheEnabled(true);
        recProduk.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recProduk.setAdapter(adapter);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.animation_slide_from_right);
        recProduk.setLayoutAnimation(animation);
        adapter.notifyDataSetChanged();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", "" + width);
        Log.d("height", "" + height);

        if (width == 1920 && height == 1200){

        }

    }
}