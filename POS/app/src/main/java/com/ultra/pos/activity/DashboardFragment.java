package com.ultra.pos.activity;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.ultra.pos.R;
import com.ultra.pos.adapter.AdapterPilihProduk;
import com.ultra.pos.api.APIConnect;
import com.ultra.pos.api.APIUrl;
import com.ultra.pos.api.BaseApiInterface;
import com.ultra.pos.api.SharedPrefManager;
import com.ultra.pos.model.Produk;
import com.ultra.pos.model.ProdukModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    Context context;
    View layout;
    RecyclerView recProduk;
    private AdapterPilihProduk adapter;
    boolean status = false;
    LinearLayout linearLihatPesanan;
    BaseApiInterface mApiInterface;
    APIConnect mApiConnect;
    SharedPrefManager pref;
    ArrayList<ProdukModel> dataProduk;
    List<Produk> produk;
    SearchView srcProdukList;
    int posfrag = 0;

    public static DashboardFragment newInstance(int position){
        DashboardFragment dashboardFragment = new DashboardFragment();

        return dashboardFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        layout = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recProduk = layout.findViewById(R.id.rvDashboardListProduk);

        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        linearLihatPesanan = view.findViewById(R.id.llDashboardLihatPesanan);

        srcProdukList = view.findViewById(R.id.svDashboardNamaProduk);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", "" + width);
        Log.d("height", "" + height);

//        if (width == 720 && height == 1280){
//
//        }

        Log.e("HENING", " == "+((Dashboard)context).gettabpos());
        showrecycler();
    }

    public void showrecycler(){

        List<ProdukModel> produkModels = new ArrayList<ProdukModel>();
        produkModels.clear();

        produkModels = ((Dashboard)context).getProdukModels(Integer.parseInt(((Dashboard)context).gettabpos()));

        produk = new ArrayList<Produk>();
        produk.clear();

        for(int i=0;i<produkModels.size();i++){

            ProdukModel prod = produkModels.get(i);
            if(prod.getDataProduk().size()==0){

            }else{
                for(int j=0;j<prod.getDataProduk().size();j++){

                    Produk isiprod = prod.getDataProduk().get(j);


                    Log.e("ONCE", " == "+prod.getNamaKategori() + " --> "+isiprod.getNamaProduk());
                }
            }
        }

        produk = produkModels.get(0).getDataProduk();

        adapter = new AdapterPilihProduk(getContext(), produk);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recProduk.setLayoutManager(mLayoutManager);
        recProduk.setItemAnimator(new DefaultItemAnimator());
        recProduk.setItemViewCacheSize(produk.size());
        recProduk.setDrawingCacheEnabled(true);
        recProduk.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recProduk.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        srcProdukList.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}