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

//    public void getProdukList(){
//
//        pref = new SharedPrefManager(getContext());
//        HashMap<String, String> kategori = pref.getKategoriDetails();
//        HashMap<String, String> user = pref.getUserDetails();
//        String idKategoriProduk = kategori.get(SharedPrefManager.ID_KATEGORI);
//        String idTB = user.get(SharedPrefManager.ID_TB);
//        String idOutlet = user.get(SharedPrefManager.ID_OUTLET);
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("idkategori", idKategoriProduk);
//        params.put("idtb", idTB);
//        params.put("idoutlet", idOutlet);
//        mApiInterface = APIUrl.getAPIService();
//        mApiInterface.getProdukList(params, idKategoriProduk, idTB, idOutlet).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()){
//                    try{
//
//                        String result = response.body().string();
//
//                        JSONObject jsonResult = new JSONObject(result);
//                        jsonResult.getString("success");
//                        jsonResult.getString("message");
//
//                        JSONArray array = jsonResult.getJSONArray("info");
//                        String[] idProduk = new String[array.length()];
//                        String[] idBusiness = new String[array.length()];
//                        String[] idOutlet = new String[array.length()];
//                        String[] namaOutlet = new String[array.length()];
//                        String[] namaProduk = new String[array.length()];
//                        String[] variant = new String[array.length()];
//                        String[] idKategori = new String[array.length()];
//                        String[] namaVariant = new String[array.length()];
//                        String[] statusProduk = new String[array.length()];
//                        String[] fotoProduk = new String[array.length()];
//                        String[] harga = new String[array.length()];
//                        dataProduk = new ArrayList<>();
//                        dataProduk.clear();
//                        for (int i = 0; i< array.length();i++){
//                            JSONObject objProduk = array.getJSONObject(i);
//                            ProdukModel produkModel = new ProdukModel(
//                                    idProduk[i] = objProduk.getString("idproduk"),
//                                    idBusiness[i] = objProduk.getString("idbusiness"),
//                                    idOutlet[i] = objProduk.getString("idoutlet"),
//                                    namaOutlet[i] = objProduk.getString("name_outlet"),
//                                    namaProduk[i] = objProduk.getString("nama_produk"),
//                                    variant[i] = objProduk.getString("variant"),
//                                    idKategori[i] = objProduk.getString("idkategori"),
//                                    namaVariant[i] = objProduk.getString("nama_variant"),
//                                    statusProduk[i] = objProduk.getString("status_produk"),
//                                    fotoProduk[i] = objProduk.getString("foto_produk"),
//                                    harga[i] = objProduk.getString("harga")
//                            );
//
//                            if (idKategori[i].equals(idKategoriProduk)){
//                                for (int j = 0; j < array.length(); j++){
//                                    produkModel.setIdProduk(idProduk[j]);
//                                    produkModel.setIdBusiness(idBusiness[j]);
//                                    produkModel.setIdOutlet(idOutlet[j]);
//                                    produkModel.setNamaOutlet(namaOutlet[j]);
//                                    produkModel.setNamaProduk(namaProduk[j]);
//                                    produkModel.setVariant(variant[j]);
//                                    produkModel.setIdKategori(idKategori[j]);
//                                    produkModel.setNamaVariant(namaVariant[j]);
//                                    produkModel.setStatusProduk(statusProduk[j]);
//                                    produkModel.setGambarProduk(fotoProduk[j]);
//                                    produkModel.setHargaProduk(harga[j]);
//
//                                    dataProduk.add(produkModel);
//                                }
//                            }
//                        }
//
//                        for (int k =0; k < dataProduk.size();k++){
//
//                        }
//
//                        adapter = new AdapterPilihProduk(getContext(), dataProduk);
//                        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                        recProduk.setLayoutManager(mLayoutManager);
//                        recProduk.setItemAnimator(new DefaultItemAnimator());
//                        recProduk.setItemViewCacheSize(dataProduk.size());
//                        recProduk.setDrawingCacheEnabled(true);
//                        recProduk.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//                        recProduk.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
////                            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(DashboardFragment.this.getContext(), R.anim.animation_slide_from_right);
////                            recProduk.setLayoutAnimation(animation);
//                    } catch (JSONException e){
//                        e.printStackTrace();
//                    } catch (IOException e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//    }

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

                            int pos = 0;
                            if(jsonArray.toString().equals("[]")){

                            }else{
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject objisinya = jsonArray.getJSONObject(i);
                                    if (objisinya.getString("id ").equals(idKategori)){
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