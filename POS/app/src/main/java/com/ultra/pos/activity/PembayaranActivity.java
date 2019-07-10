package com.ultra.pos.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

import com.ultra.pos.R;
import com.ultra.pos.adapter.AdapterEDC;
import com.ultra.pos.adapter.AdapterPesanan;
import com.ultra.pos.adapter.AdapterRingkasanOrder;
import com.ultra.pos.adapter.AdapterTipePenjualan;
import com.ultra.pos.adapter.AdapterTransaksiTersimpan;
import com.ultra.pos.api.APIUrl;
import com.ultra.pos.api.BaseApiInterface;
import com.ultra.pos.api.SharedPrefManager;
import com.ultra.pos.model.EDCModel;
import com.ultra.pos.model.OrderModel;
import com.ultra.pos.model.PostTransaksiListModel;
import com.ultra.pos.model.TransaksiModel;

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

public class PembayaranActivity extends AppCompatActivity {


    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextView total;
    int totalKembalian,cash=0;
    TextInputEditText jumlahLain;
    RecyclerView recEDC;
    private AdapterEDC adapter;
    private List<EDCModel> listEDC;
    private SharedPrefManager pref;
    private String idbusiness,idoutlet;
    private BaseApiInterface mApiInterface;
    Button konfirmasibayar,pas,limapuluh,seratus,duaratus,tunai,edc;
    String totalbayar, idtb,  idcop,  idctm, noinv_transHD, diskon, statusBayar, idUser, idSaltype, totalTransHD;


    List<String> dataIdProduk = new ArrayList<>();
    List<String> dataNamaProduk = new ArrayList<>();
    List<String> dataIdVariant = new ArrayList<>();
    List<String> dataNamaVariant = new ArrayList<>();
    List<String> dataHargaVariant = new ArrayList<>();
    private ArrayList<PostTransaksiListModel> postTransaksiListModels;
    private String typeTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        konfirmasibayar=findViewById(R.id.btnBayarPembayaran);
        pas=findViewById(R.id.btnUangPas);
        limapuluh=findViewById(R.id.btnUang50K);
        seratus=findViewById(R.id.btnUang100K);
        duaratus=findViewById(R.id.btnUang200K);
        total=findViewById(R.id.tvTotalPembayaranAct);
        jumlahLain=findViewById(R.id.tietJumlahLain);
        tunai=findViewById(R.id.btnTunai);
        edc=findViewById(R.id.btnEDC);

        totalbayar = getIntent().getStringExtra("totalbyr");
        total.setText("Rp. "+totalbayar);
        Bundle bundle = getIntent().getExtras();

        postTransaksiListModels = new ArrayList<>();

        Intent i = getIntent();
        idtb = getIntent().getStringExtra("idtb");
        idbusiness = getIntent().getStringExtra("idbusiness");
        idcop = getIntent().getStringExtra("idcop");
        idoutlet = getIntent().getStringExtra("idoutlet");
        idctm = getIntent().getStringExtra("idctm");
        noinv_transHD = getIntent().getStringExtra("noinv_transHD");
        diskon = getIntent().getStringExtra("diskon");
        statusBayar = getIntent().getStringExtra("status_transHD");
        dataIdProduk = bundle.getStringArrayList("idProduk");
        dataNamaProduk = bundle.getStringArrayList("namaProduk");
        dataIdVariant = bundle.getStringArrayList("idVariant");
        dataNamaVariant = bundle.getStringArrayList("namaVariant");
        dataHargaVariant = bundle.getStringArrayList("hargaPesanan");
        idUser = getIntent().getStringExtra("iduser");
        idSaltype = getIntent().getStringExtra("idsaltype");
        totalTransHD = getIntent().getStringExtra("total_transHD");

        pas.setOnClickListener(v -> {
            totalKembalian=0;
            Log.i("Pas",""+totalKembalian);
        });

        limapuluh.setOnClickListener(v -> {
            totalKembalian=50000-Integer.parseInt(totalbayar);
            Log.i("Sisa 50",""+totalKembalian);
        });

        String[][] dataIdProdukList = new String[dataIdProduk.size()][5];

        for (int j=0;j<dataIdProduk.size();j++){
            dataIdProdukList[j][0] = dataIdProduk.get(j);
            dataIdProdukList[j][1] = dataNamaProduk.get(j);
            dataIdProdukList[j][2] = dataIdVariant.get(j);
            dataIdProdukList[j][3] = dataNamaVariant.get(j);
            dataIdProdukList[j][4] = dataHargaVariant.get(j);

            Log.i("Hasil",""+dataIdProdukList[j][0]+" "+dataIdProdukList[j][1]+" "+dataIdProdukList[j][2]+" "+dataIdProdukList[j][3]);
        }

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", "" + width);
        Log.d("height", "" + height);

        if (width == 720 && height == 1280){
            total.setText("Rp. "+totalbayar);
            pas.setOnClickListener(v -> {
                totalKembalian=0;
                Log.i("Pas",""+totalKembalian);
            });

            limapuluh.setOnClickListener(v -> {
                totalKembalian=50000-Integer.parseInt(totalbayar);
                Log.i("Sisa 50",""+totalKembalian);
            });

            seratus.setOnClickListener(v -> {
                totalKembalian=100000-Integer.parseInt(totalbayar);
                Log.i("Sisa 100",""+totalKembalian);
            });

            duaratus.setOnClickListener(v -> {
                totalKembalian=200000-Integer.parseInt(totalbayar);
                Log.i("Sisa 200",""+totalKembalian);
            });
            konfirmasibayar.setOnClickListener(v -> {
                Intent intent = new Intent(this, PembayaranSuksesActivity.class);
                if(jumlahLain.getText().toString().equals("0")){
                    intent.putExtra("kembalian", String.valueOf(totalKembalian));
                    Log.i("Bukan Jml lain",""+totalKembalian);
                }else{
                    totalKembalian=Integer.parseInt(jumlahLain.getText().toString())-Integer.parseInt(totalbayar);
                    intent.putExtra("kembalian", String.valueOf(totalKembalian));
                    Log.i("Jml Lain",""+totalKembalian);
                }

                startActivity(intent);
            });
            edc.setOnClickListener(v -> {
                edc_list();
            });
        }else {
            total.setText("Rp. " + totalTransHD);

            konfirmasibayar.setOnClickListener(v -> {
                Intent intent = new Intent(this, PembayaranSuksesActivity.class);
                if(jumlahLain.getText().toString().equals("0")){
                    intent.putExtra("kembalian", String.valueOf(totalKembalian));
                    Log.i("Bukan Jml lain",""+totalKembalian);
                }else{
                    totalKembalian=Integer.parseInt(jumlahLain.getText().toString())-Integer.parseInt(totalTransHD);
                    intent.putExtra("kembalian", String.valueOf(totalKembalian));
                    Log.i("Jml Lain",""+totalKembalian);
                }

                startActivity(intent);
            });

            pas.setOnClickListener(v -> {
                totalKembalian=0;
                Log.i("Pas",""+totalKembalian);
            });

            limapuluh.setOnClickListener(v -> {
                totalKembalian=50000-Integer.parseInt(totalTransHD);
                Log.i("Sisa 50",""+totalKembalian);
            });

            seratus.setOnClickListener(v -> {
                totalKembalian=100000-Integer.parseInt(totalTransHD);
                Log.i("Sisa 100",""+totalKembalian);
            });

            duaratus.setOnClickListener(v -> {
                totalKembalian=200000-Integer.parseInt(totalTransHD);
                Log.i("Sisa 200",""+totalKembalian);
            });

            edc.setOnClickListener(v -> {
                edc_list();
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu_pembayaran,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.catatan){
            dialogMenuCatatan();
        }
        return true;
    }

    public void dialogMenuCatatan(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_catatan,null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        dialog.show();
    }

    public void edc_list(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_edc, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        recEDC = dialogView.findViewById(R.id.rvEDC);

        getListEDC();

        dialog.setPositiveButton("Submit",(dialog1, which) -> {
            dialog1.dismiss();
//            edc.setBackgroundColor(Color.parseColor("#BFEBFF"));
        });

        dialog.setNegativeButton("Batal",(dialog1, which) -> {
            typeTrans="";
            dialog1.dismiss();
        });
        dialog.show();
    }
    public void getListEDC(){
        pref = new SharedPrefManager(this);
        HashMap<String, String> user = pref.getUserDetails();
        String idBusiness = user.get(SharedPrefManager.ID_BUSINESS);
        String idOutlet = user.get(SharedPrefManager.ID_OUTLET);
        idbusiness = idBusiness;
        idoutlet = idOutlet;

        listEDC = new ArrayList<>();
        listEDC.clear();

        HashMap<String, String> params = new HashMap<>();
        params.put("idbusiness",idbusiness);
        params.put("idOulet",idoutlet);

        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getEDC(params,idbusiness,idoutlet).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);
                        JSONArray array = jsonResult.getJSONArray("info");
                        Log.i("Result",""+array);

                        for (int i = 0; i<array.length(); i++){
                            JSONObject objKategori = array.getJSONObject(i);
                            listEDC.add(i, new EDCModel(""+objKategori.getString("idpay"),""+objKategori.getString("nama_pay")));
                        }

                        adapter = new AdapterEDC(PembayaranActivity.this, listEDC);
                        final RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(PembayaranActivity.this);
                        recEDC.setLayoutManager(mLayoutManager2);
                        recEDC.setItemAnimator(new DefaultItemAnimator());
                        recEDC.setItemViewCacheSize(listEDC.size());
                        recEDC.setDrawingCacheEnabled(true);
                        recEDC.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                        recEDC.setAdapter(adapter);
                        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(PembayaranActivity.this, R.anim.animation_slide_from_right);
                        recEDC.setLayoutAnimation(animation);
                        adapter.notifyDataSetChanged();
                    }catch (JSONException e){
                        e.printStackTrace();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void settype_pembayaran(String type){
        typeTrans=type;
        Log.i("Nama",""+typeTrans);
    }
}
