package com.aiopos.apps.activity;

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

import com.aiopos.apps.R;
import com.aiopos.apps.adapter.AdapterPilihProduk;
import com.aiopos.apps.api.APIConnect;
import com.aiopos.apps.api.BaseApiInterface;
import com.aiopos.apps.api.SharedPrefManager;
import com.aiopos.apps.model.Produk;
import com.aiopos.apps.model.ProdukModel;

import java.util.ArrayList;

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
    ArrayList<Produk> produk;
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

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", "" + width);
        Log.d("height", "" + height);

        if (getResources().getConfiguration().smallestScreenWidthDp == 360){
            srcProdukList = view.findViewById(R.id.svDashboardNamaProduk);
            showrecycler();
        }else{
            srcProdukList = view.findViewById(R.id.svDashboardNamaProduk);
            showrecycler();
        }

        Log.e("HENING", " == "+((Dashboard)context).gettabpos());
    }

    public void showrecycler(){

        ArrayList<ProdukModel> produkModels = new ArrayList<ProdukModel>();
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
                    Log.i("data",""+isiprod.getNamaVariant());


                    Log.e("ONCE", " == "+prod.getNamaKategori() + " --> "+isiprod.getNamaProduk());
                }
            }
        }

        produk = produkModels.get(0).getDataProduk();

        Log.i("data",""+produk);
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

//    public void showrecyclerMobile(){
//
//        List<ProdukModel> produkModels = new ArrayList<ProdukModel>();
//        produkModels.clear();
//
//        produkModels = ((Dashboard)context).getProdukModels(Integer.parseInt(((Dashboard)context).gettabpos()));
//
//        produk = new ArrayList<Produk>();
//        produk.clear();
//
//        for(int i=0;i<produkModels.size();i++){
//
//            ProdukModel prod = produkModels.get(i);
//            if(prod.getDataProduk().size()==0){
//
//            }else{
//                for(int j=0;j<prod.getDataProduk().size();j++){
//
//                    Produk isiprod = prod.getDataProduk().get(j);
//
//
//                    Log.e("ONCE", " == "+prod.getNamaKategori() + " --> "+isiprod.getNamaProduk());
//                }
//            }
//        }
//
//        produk = produkModels.get(0).getDataProduk();
//
//        adapter = new AdapterPilihProduk(getContext(), produk);
//        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        recProduk.setLayoutManager(mLayoutManager);
//        recProduk.setItemAnimator(new DefaultItemAnimator());
//        recProduk.setItemViewCacheSize(produk.size());
//        recProduk.setDrawingCacheEnabled(true);
//        recProduk.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//        recProduk.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }
}