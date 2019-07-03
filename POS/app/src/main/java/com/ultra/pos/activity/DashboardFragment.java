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
import com.ultra.pos.api.APIConnect;
import com.ultra.pos.api.APIUrl;
import com.ultra.pos.api.BaseApiInterface;
import com.ultra.pos.api.SharedPrefManager;
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

    View layout;
    RecyclerView recProduk;
    private AdapterPilihProduk adapter;
    public static final String API_Produk = "http://pos.ultrapreneur.id/produk_get_kategori.php";
    boolean status = false;
    LinearLayout linearLihatPesanan;
    BaseApiInterface mApiInterface;
    APIConnect mApiConnect;
    SharedPrefManager pref;
    ArrayList<ProdukModel> dataProduk;

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
        String idKategori = kategori.get(SharedPrefManager.ID_KATEGORI);
        String idTB = user.get(SharedPrefManager.ID_TB);
        String idOutlet = user.get(SharedPrefManager.ID_OUTLET);

        dataProduk = new ArrayList<ProdukModel>();
        dataProduk.clear();

        HashMap<String, String> params = new HashMap<>();
        params.put("idkategori", idKategori);
        params.put("idtb", idTB);
        params.put("idoutlet", idOutlet);

        Log.i("Isi",idKategori+idTB+idOutlet);
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getProdukList(params, idKategori, idTB, idOutlet).enqueue(new Callback<ResponseBody>() {
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
                                    dataProduk.add(i, new ProdukModel(
                                            objisinya.getString("idproduk"),
                                            objisinya.getString("idbusiness"),
                                            objisinya.getString("idoutlet"),
                                            objisinya.getString("name_outlet"),
                                            objisinya.getString("nama_produk"),
                                            objisinya.getString("variant"),
                                            objisinya.getString("idkategori"),
                                            objisinya.getString("nama_variant"),
                                            objisinya.getString("status_produk"),
                                            objisinya.getString("foto_produk"),
                                            objisinya.getString("harga")));
//                                    if (objisinya.getString("idkategori ").equals(idKategori)){
//                                        dataProduk.add(i, new ProdukModel(
//                                                objisinya.getString("idproduk"),
//                                                objisinya.getString("idbusiness"),
//                                                objisinya.getString("idoutlet"),
//                                                objisinya.getString("name_outlet"),
//                                                objisinya.getString("nama_produk"),
//                                                objisinya.getString("variant"),
//                                                objisinya.getString("idkategori"),
//                                                objisinya.getString("nama_variant"),
//                                                objisinya.getString("status_produk"),
//                                                objisinya.getString("foto_produk"),
//                                                objisinya.getString("harga")));
//                                        pos++;
//                                    }
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    } catch (IOException e) {
                        e.printStackTrace();


                    }

                    adapter = new AdapterPilihProduk(getContext(), dataProduk);
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