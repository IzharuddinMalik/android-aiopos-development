package com.aio.pos.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aio.pos.R;
import com.aio.pos.adapter.AdapterPesanan;
import com.aio.pos.adapter.AdapterTipePenjualan;
import com.aio.pos.api.APIUrl;
import com.aio.pos.model.Produk;
import com.aio.pos.model.TipeModel;
import com.aio.pos.api.BaseApiInterface;
import com.aio.pos.api.SharedPrefManager;

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

public class RingkasanOrderActivity extends AppCompatActivity {
    RecyclerView recOrder,recTipe;
    Button simpan,bayar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextView TOTAL,Subtotal,Diskon;
    EditText diskon;
    private Context mCtx;
    LinearLayout subtotal,diskonll;
    private List<Produk> listOrder;
    private List<TipeModel> listTipe;
    private AdapterPesanan adapter;
    private AdapterTipePenjualan adapter2;
    ArrayList<String> dataidProduk=new ArrayList<String>();
    ArrayList<String> datanamaProduk=new ArrayList<String>();
    ArrayList<String> dataidVariant=new ArrayList<String>();
    ArrayList<String> datanamaVariant=new ArrayList<String>();
    ArrayList<String> datahargaPesanan=new ArrayList<String>();
    ArrayList<String> datajumlahPesanan=new ArrayList<String>();

    ArrayList<String> arridProduk= new ArrayList<String>();
    ArrayList<String> arrnamaProduk= new ArrayList<String>();
    ArrayList<String> arridVariant= new ArrayList<String>();
    ArrayList<String> arrnamaVariant= new ArrayList<String>();
    ArrayList<String> arrhargaPesanan= new ArrayList<String>();
    ArrayList<String> arrjumlahPesanan= new ArrayList<String>();

    List<String> idProduk=new ArrayList<String>();
    List<String> namaProduk=new ArrayList<String>();
    List<String> idVariant=new ArrayList<String>();
    List<String> namaVariant=new ArrayList<String>();
    List<String> hargaPesanan=new ArrayList<String>();
    List<String> jumlahPesanan=new ArrayList<String>();
    int total,disc,sub=0;
    private SharedPrefManager pref;
    private String idbusiness,idoutlet;
    private BaseApiInterface mApiInterface;
    public String SalesType,IDSalType, idtb, diskonBaru;
    private String idcop, idctm, noinv_transHD, statusBayar, idUser, idSaltype, totalTransHD, idTax, idSaltype2, namaPelanggan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringkasan_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        idtb = getIntent().getStringExtra("idtb");
        idbusiness = getIntent().getStringExtra("idbusiness");
        idcop = getIntent().getStringExtra("idcop");
        idoutlet = getIntent().getStringExtra("idoutlet");
        idctm = getIntent().getStringExtra("idctm");
        Log.i("isi ctm","isi---->"+idctm);
        noinv_transHD = getIntent().getStringExtra("noinv_transHD");
        diskonBaru = getIntent().getStringExtra("diskon");
        statusBayar = getIntent().getStringExtra("status_transHD");
        dataidProduk = bundle.getStringArrayList("idProduk");
        datanamaProduk = bundle.getStringArrayList("namaProduk");
        dataidVariant = bundle.getStringArrayList("idVariant");
        datanamaVariant = bundle.getStringArrayList("namaVariant");
        datahargaPesanan = bundle.getStringArrayList("hargaPesanan");
        idUser = getIntent().getStringExtra("iduser");
        idSaltype = getIntent().getStringExtra("idsaltype");
        totalTransHD = getIntent().getStringExtra("total_transHD");
        datajumlahPesanan = bundle.getStringArrayList("jumlahPesanan");
        idTax = getIntent().getStringExtra("idtax");
        namaPelanggan = getIntent().getStringExtra("namaPelanggan");

        selectionData();

        recOrder = findViewById(R.id.rvRingkasanOrder);
        simpan=findViewById(R.id.btnSimpanOrder);
        bayar=findViewById(R.id.btnBayarOrder);
        listOrder = new ArrayList<>();
        listOrder.clear();

        for(int i=0;i<idProduk.size();i++){
            listOrder.add(i, new Produk(""+idProduk.get(i), ""+namaProduk.get(i), ""+idVariant.get(i),""+namaVariant.get(i),"@ Rp. "+hargaPesanan.get(i), "",""+jumlahPesanan.get(i),""));
            total=total + Integer.parseInt(hargaPesanan.get(i)) * Integer.parseInt(jumlahPesanan.get(i));
       }

        diskonll=findViewById(R.id.llLinearDiskon);
        subtotal=findViewById(R.id.llLinearSubtotal);
        TOTAL=findViewById(R.id.tvTotalOrder);
        Subtotal=findViewById(R.id.tvSubtotal);
        Diskon=findViewById(R.id.tvDiskonOrderLast);

        TOTAL.setText("Rp. "+total);

        adapter = new AdapterPesanan(this, listOrder);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recOrder.setLayoutManager(mLayoutManager);
        recOrder.setItemAnimator(new DefaultItemAnimator());
        recOrder.setItemViewCacheSize(listOrder.size());
        recOrder.setDrawingCacheEnabled(true);
        recOrder.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recOrder.setAdapter(adapter);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.animation_slide_from_right);
        recOrder.setLayoutAnimation(animation);
        adapter.notifyDataSetChanged();

        recTipe = findViewById(R.id.rvTipePenjualan);
        getAlllistSalesType();

        simpan.setOnClickListener(v -> {
            startActivity(new Intent(this,TransaksiTersimpanActivity.class));
            finish();
        });

        bayar.setOnClickListener(v -> {
            HashMap<String, String> user = pref.getUserDetails();
            String idUser = user.get(SharedPrefManager.ID_USER);
            String idTb = user.get(SharedPrefManager.ID_TB);
            String idTax = user.get(SharedPrefManager.ID_TAX);

            Intent intent = new Intent(this, PembayaranActivity.class);
            intent.putExtra("diskon", String.valueOf(disc));
            intent.putExtra("totalbyr", String.valueOf(total));
            intent.putExtra("salestype", String.valueOf(IDSalType));
            intent.putExtra("idtb", idtb);
            intent.putExtra("idbusiness", idbusiness);
            intent.putExtra("idcop", "0");
            intent.putExtra("idoutlet", idoutlet);
            intent.putExtra("idctm", getIntent().getStringExtra("idctm"));
            intent.putExtra("noinv_transHD", "0");
            intent.putExtra("diskon", ""+disc);
            intent.putExtra("status_transHD", "1");
            intent.putExtra("idProduk", dataidProduk);
            intent.putExtra("namaProduk", datanamaProduk);
            intent.putExtra("idVariant", dataidVariant);
            intent.putExtra("namaVariant", datanamaVariant);
            intent.putExtra("hargaPesanan", datahargaPesanan);
            intent.putExtra("jumlahPesanan", datajumlahPesanan);
            intent.putExtra("iduser", idUser);
            intent.putExtra("idsaltype", IDSalType);
            intent.putExtra("total_transHD", ""+total);
            intent.putExtra("idtax", idTax);
//            intent.putExtra("totalbyr", String.valueOf(total));

            Log.i("Diskon",""+disc);
            Log.i("Total",""+total);
            Log.i("Sales Type",""+IDSalType);
            Log.i("IDPRODUK", " === " +arridProduk);

            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.diskon && dataidProduk.size()!=0){
            dialogMenuDiskon();
        } else if (item.getItemId() == R.id.hapus_pesanan && dataidProduk.size()!=0) {
            dialogMenuHapus();
        }
        return true;
    }

    public void dialogMenuDiskon(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_diskon,null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        diskon=dialogView.findViewById(R.id.edtDialogFormNilaiDiskon);
        dialog.setPositiveButton("Simpan",(dialog1, which) -> {
            if((diskon.getText()).equals("")){

            }else{
                diskonll.setVisibility(View.VISIBLE);
                subtotal.setVisibility(View.VISIBLE);

                disc=total*Integer.parseInt(diskon.getText().toString())/100;
                sub=total;
                total=sub-disc;
                Subtotal.setText("Rp. "+sub);
                Diskon.setText("- Rp. "+disc);
                TOTAL.setText("Rp. "+(total));
                dialog1.dismiss();
            }
        });
        dialog.show();
    }

    public void dialogMenuHapus(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_hapus,null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        dialog.setPositiveButton("Hapus",(dialog1, which) -> {
            resetOrder();
            startActivity(new Intent(this, Dashboard.class));
            finish();
        });

        dialog.setNegativeButton("Batalkan",(dialog1, which) -> {
            dialog1.dismiss();
        });

        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    public void back(View view){
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("idtb", idtb);
        intent.putExtra("idbusiness", idbusiness);
        intent.putExtra("idcop", "0");
        intent.putExtra("idoutlet", idoutlet);
        intent.putExtra("idctm", getIntent().getStringExtra("idctm"));
        intent.putExtra("noinv_transHD", "0");
        intent.putExtra("diskon", diskonBaru);
        intent.putExtra("status_transHD", "1");
        intent.putExtra("idProduk", arridProduk);
        intent.putExtra("namaProduk", arrnamaProduk);
        intent.putExtra("idVariant", arridVariant);
        intent.putExtra("namaVariant", arrnamaVariant);
        intent.putExtra("hargaPesanan", arrhargaPesanan);
        intent.putExtra("jumlahPesanan", arrjumlahPesanan);
        intent.putExtra("idctm", getIntent().getStringExtra("idctm"));
        intent.putExtra("iduser", idUser);
        intent.putExtra("idsaltype", idSaltype2);
        intent.putExtra("idtax", idTax);
        intent.putExtra("namaPelanggan", namaPelanggan);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("idtb", idtb);
        intent.putExtra("idbusiness", idbusiness);
        intent.putExtra("idcop", "0");
        intent.putExtra("idoutlet", idoutlet);
        intent.putExtra("idctm", getIntent().getStringExtra("idctm"));
        intent.putExtra("noinv_transHD", "0");
        intent.putExtra("diskon", diskonBaru);
        intent.putExtra("status_transHD", "1");
        intent.putExtra("idProduk", arridProduk);
        intent.putExtra("namaProduk", arrnamaProduk);
        intent.putExtra("idVariant", arridVariant);
        intent.putExtra("namaVariant", arrnamaVariant);
        intent.putExtra("hargaPesanan", arrhargaPesanan);
        intent.putExtra("jumlahPesanan", arrjumlahPesanan);
        intent.putExtra("idctm", getIntent().getStringExtra("idctm"));
        intent.putExtra("iduser", idUser);
        intent.putExtra("idsaltype", idSaltype2);
        intent.putExtra("idtax", idTax);
        intent.putExtra("namaPelanggan", namaPelanggan);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void selectionData(){
        String tmp="";
        int jml=0;
        if(dataidProduk.size()==1){
            idProduk.add(dataidProduk.get(0));
            namaProduk.add(datanamaProduk.get(0));
            idVariant.add(dataidVariant.get(0));
            namaVariant.add(datanamaVariant.get(0));
            hargaPesanan.add(datahargaPesanan.get(0));
            jumlahPesanan.add(datajumlahPesanan.get(0));
        }else{
            for(int i=0;i<dataidProduk.size();i++){
                if(dataidVariant.get(i).equals("")){
                    if(tmp.equals(datanamaProduk.get(i))){

                    }else{
                        idProduk.add(dataidProduk.get(i));
                        namaProduk.add(datanamaProduk.get(i));
                        idVariant.add(dataidVariant.get(i));
                        namaVariant.add(datanamaVariant.get(i));
                        hargaPesanan.add(datahargaPesanan.get(i));
                        tmp=datanamaProduk.get(i);

                        for(int k=0;k<datanamaProduk.size();k++){
                            if(tmp.equals(datanamaProduk.get(k))){
                                jml=Integer.parseInt(datajumlahPesanan.get(k));
                            }
                        }
                        jumlahPesanan.add(""+jml);
                    }
                }else{
                    if(tmp.equals(datanamaVariant.get(i))){

                    }else{
                        idProduk.add(dataidProduk.get(i));
                        namaProduk.add(datanamaProduk.get(i));
                        idVariant.add(dataidVariant.get(i));
                        namaVariant.add(datanamaVariant.get(i));
                        hargaPesanan.add(datahargaPesanan.get(i));
                        tmp=datanamaVariant.get(i);

                        for(int k=0;k<datanamaVariant.size();k++){
                            if(tmp.equals(datanamaVariant.get(k))){
                                jml=Integer.parseInt(datajumlahPesanan.get(k));
                            }
                        }
                        jumlahPesanan.add(""+jml);
                    }
                }
            }
        }
        Log.i("Data",""+datanamaVariant.size());
    }

    public void getAlllistSalesType(){
        pref = new SharedPrefManager(this);
        HashMap<String, String> user = pref.getUserDetails();
        String idBusiness = user.get(SharedPrefManager.ID_BUSINESS);
        String idOutlet = user.get(SharedPrefManager.ID_OUTLET);
        idbusiness = idBusiness;
        idoutlet = idOutlet;

        listTipe = new ArrayList<>();
        listTipe.clear();

        HashMap<String, String> params = new HashMap<>();
        params.put("idbusiness",idbusiness);
        params.put("idOulet",idoutlet);
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getSalesType(params,idbusiness,idoutlet).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);
                        JSONArray array = jsonResult.getJSONArray("info");

                        TipeModel tipeMOdel=new TipeModel("","");
                        for (int i = 0; i<array.length(); i++){
                            JSONObject objKategori = array.getJSONObject(i);
                            objKategori.getString("idsaltype");
                            objKategori.getString("nama_saltype");

                            listTipe.add(0, new TipeModel(objKategori.getString("idsaltype"),objKategori.getString("nama_saltype")));
                        }

                        adapter2 = new AdapterTipePenjualan(RingkasanOrderActivity.this, listTipe);
                        final RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(RingkasanOrderActivity.this);
                        recTipe.setLayoutManager(mLayoutManager2);
                        recTipe.setItemAnimator(new DefaultItemAnimator());
                        recTipe.setItemViewCacheSize(listTipe.size());
                        recTipe.setDrawingCacheEnabled(true);
                        recTipe.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                        recTipe.setAdapter(adapter2);
                        LayoutAnimationController animation2 = AnimationUtils.loadLayoutAnimation(RingkasanOrderActivity.this, R.anim.animation_slide_from_right);
                        recTipe.setLayoutAnimation(animation2);
                        adapter2.notifyDataSetChanged();
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

    public void resetOrder(){
        idProduk.clear();
        namaProduk.clear();
        idVariant.clear();
        namaVariant.clear();
        hargaPesanan.clear();
        jumlahPesanan.clear();
    }

    public void saleslistener(String idsaltype,String salesType){
        IDSalType=idsaltype;
        SalesType=salesType;

        Log.i("Sales",""+SalesType);
        Log.i("ID Sal Type",""+IDSalType);
    }
}
