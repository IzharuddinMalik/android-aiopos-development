package com.ultra.pos.activity;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ultra.pos.R;
import com.ultra.pos.adapter.AdapterPesanan;
import com.ultra.pos.adapter.AdapterPilihPelanggan;
import com.ultra.pos.adapter.AdapterRingkasanOrder;
import com.ultra.pos.adapter.AdapterTipePenjualan;
import com.ultra.pos.api.APIUrl;
import com.ultra.pos.api.BaseApiInterface;
import com.ultra.pos.api.SharedPrefManager;
import com.ultra.pos.model.OrderModel;
import com.ultra.pos.model.PelangganModel;
import com.ultra.pos.model.PesananModel;
import com.ultra.pos.model.Produk;
import com.ultra.pos.model.TipeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
    List<String> dataidProduk=new ArrayList<String>();
    List<String> datanamaProduk=new ArrayList<String>();
    List<String> dataidVariant=new ArrayList<String>();
    List<String> datanamaVariant=new ArrayList<String>();
    List<String> datahargaPesanan=new ArrayList<String>();
    List<String> datajumlahPesanan=new ArrayList<String>();

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
    public String SalesType,IDSalType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringkasan_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        dataidProduk = bundle.getStringArrayList("idProduk");
        datanamaProduk = bundle.getStringArrayList("namaProduk");
        dataidVariant = bundle.getStringArrayList("idVariant");
        datanamaVariant = bundle.getStringArrayList("namaVariant");
        datahargaPesanan = bundle.getStringArrayList("hargaPesanan");
        datajumlahPesanan = bundle.getStringArrayList("jumlahPesanan");

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
            Intent intent = new Intent(this, PembayaranActivity.class);
            intent.putExtra("diskon", String.valueOf(disc));
            intent.putExtra("totalbyr", String.valueOf(total));
            intent.putExtra("salestype", String.valueOf(IDSalType));
//            intent.putExtra("totalbyr", String.valueOf(total));

            Log.i("Diskon",""+disc);
            Log.i("Total",""+total);
            Log.i("Sales Type",""+IDSalType);

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
            diskonll.setVisibility(View.VISIBLE);
            subtotal.setVisibility(View.VISIBLE);

            disc=total*Integer.parseInt(diskon.getText().toString())/100;
            sub=total;
            total=sub-disc;
            Subtotal.setText("Rp. "+sub);
            Diskon.setText("- Rp. "+disc);
            TOTAL.setText("Rp. "+(total));
            dialog1.dismiss();
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
        startActivity(new Intent(this, Dashboard.class));
        resetOrder();
//        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Dashboard.class));
        resetOrder();
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
                if(dataidVariant.get(i).equals("0")){
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
