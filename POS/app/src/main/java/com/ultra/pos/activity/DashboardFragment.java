package com.ultra.pos.activity;

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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

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

        linearLihatPesanan = view.findViewById(R.id.llDashboardLihatPesanan);

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

        getProdukList();
    }

    public void getProdukList(){

        pref = new SharedPrefManager(getContext());
        HashMap<String, String> kategori = pref.getKategoriDetails();
        HashMap<String, String> user = pref.getUserDetails();
        String idBusiness = user.get(SharedPrefManager.ID_BUSINESS);
        String idTB = user.get(SharedPrefManager.ID_TB);
        String idOutlet = user.get(SharedPrefManager.ID_OUTLET);
        String idKategori = kategori.get(SharedPrefManager.ID_KATEGORI);

        dataProduk = new ArrayList<ProdukModel>();
        dataProduk.clear();

        produk = new ArrayList<Produk>();
        produk.clear();

        HashMap<String, String> params = new HashMap<>();
        params.put("idbusiness", idBusiness);
        params.put("idtb", idTB);
        params.put("idoutlet", idOutlet);

        Log.i("Isi",idKategori+idTB+idOutlet);
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getProduk(params, idBusiness, idTB, idOutlet).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    String result = null;
                    try {
                        result = response.body().string();
                        try {
                            JSONObject jsonRESULTS = new JSONObject(result);
                            JSONArray jsonArray = jsonRESULTS.getJSONArray("info");
                            Log.i("Isi",""+jsonArray);

                            int pos = 0;

                            if(jsonArray.toString().equals("[]")){
                            }else{
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject objisinya = jsonArray.getJSONObject(i);
                                    JSONArray arrayProduk = objisinya.getJSONArray("produk");
                                    if(arrayProduk.length()==0){
                                        dataProduk.add(i, new ProdukModel(objisinya.getString("idkategori"), objisinya.getString("nama_kategori"), produk));
                                    }else{
                                        for (int j=0;j<arrayProduk.length();j++){
                                            JSONObject objprod = arrayProduk.getJSONObject(j);
                                            produk.add(j, new Produk(objprod.getString("idproduk"), objprod.getString("nama_produk"), objprod.getString("foto_produk"), objprod.getString("harga_produk"), objprod.getString("idkategori")));
                                        }
                                        dataProduk.add(pos, new ProdukModel(objisinya.getString("idkategori"), objisinya.getString("nama_kategori"), produk));
                                        pos++;
                                    }

                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    } catch (IOException e) {
                        e.printStackTrace();


                    }

                    adapter = new AdapterPilihProduk(getContext(), produk);
                    final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    recProduk.setLayoutManager(mLayoutManager);
                    recProduk.setItemAnimator(new DefaultItemAnimator());
                    recProduk.setItemViewCacheSize(dataProduk.size());
                    recProduk.setDrawingCacheEnabled(true);
                    recProduk.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                    recProduk.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}