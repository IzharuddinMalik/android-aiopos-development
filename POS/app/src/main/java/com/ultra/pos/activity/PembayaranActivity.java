package com.ultra.pos.activity;

import android.content.Intent;
import android.graphics.Point;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ultra.pos.R;
import com.ultra.pos.api.APIConnect;
import com.ultra.pos.api.APIUrl;
import com.ultra.pos.api.BaseApiInterface;
import com.ultra.pos.model.PostTransaksiListModel;
import com.ultra.pos.model.PostTransaksiModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranActivity extends AppCompatActivity {


    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextView total;
    int totalKembalian=0;
    TextInputEditText jumlahLain;
    String totalbayar, idtb, idbusiness, idcop, idoutlet, idctm, noinv_transHD, diskon, statusBayar, idUser, idSaltype, totalTransHD, idTax;
    Button konfirmasibayar,pas,limapuluh,seratus,duaratus;
    String[] produkList;
    BaseApiInterface mApiInterface;
    APIConnect mApiConnect;

    List<String> dataIdProduk = new ArrayList<>();
    List<String> dataNamaProduk = new ArrayList<>();
    List<String> dataIdVariant = new ArrayList<>();
    List<String> dataNamaVariant = new ArrayList<>();
    List<String> dataHargaVariant = new ArrayList<>();
    List<String> dataIdTax = new ArrayList<>();
    List<String> dataJumlah = new ArrayList<>();
    private ArrayList<PostTransaksiListModel> postTransaksiListModels;

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

        totalbayar = getIntent().getStringExtra("totalbyr");

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
        dataJumlah = bundle.getStringArrayList("jumlahPesanan");
        idTax = getIntent().getStringExtra("idtax");


        Log.i("TRANSAKSI COBA", " === " +idtb +" " +idbusiness+" "+idcop+" "+idoutlet+" "+idctm+" "+noinv_transHD+" "+diskon+" "+statusBayar+" "+dataIdProduk +" "+dataNamaProduk+" "+dataIdVariant+" "+dataNamaVariant+" "+dataHargaVariant+ " "+ dataJumlah+" "+idUser+" "+idSaltype+" "+totalTransHD);


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
                bayar();

                startActivity(intent);
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
                bayar();

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

    private void bayar(){
        postTransaksiListModels = new ArrayList<>();

        String[][] dataIdProdukList = new String[dataIdProduk.size()][6];

        PostTransaksiModel postTransaksiModel = new PostTransaksiModel();
        postTransaksiModel.setIdTb(idtb);
        postTransaksiModel.setIdBusiness(idbusiness);
        postTransaksiModel.setIdCop(idcop);
        postTransaksiModel.setIdOutlet(idoutlet);
        postTransaksiModel.setIdCtm(idctm);
        postTransaksiModel.setNoInvTransHD(noinv_transHD);
        postTransaksiModel.setDiskonTransaksi(diskon);
        postTransaksiModel.setStatusTransHD(statusBayar);
        for (int j=0;j<dataIdProduk.size();j++){
            dataIdProdukList[j][0] = dataIdProduk.get(j);
            dataIdProdukList[j][1] = dataNamaProduk.get(j);
            dataIdProdukList[j][2] = dataIdVariant.get(j);
            dataIdProdukList[j][3] = dataNamaVariant.get(j);
            dataIdProdukList[j][4] = dataHargaVariant.get(j);
            dataIdProdukList[j][5] = dataJumlah.get(j);

            Log.i("Hasil",""+dataIdProdukList[j][0] + dataIdProdukList[j][2] + "0" + dataIdProdukList[j][5] + dataIdProdukList[j][4] + "0");
            postTransaksiListModels.add(new PostTransaksiListModel(dataIdProdukList[j][0], dataIdProdukList[j][2],"0", dataIdProdukList[j][5], dataIdProdukList[j][4], "0"));
            postTransaksiModel.setPostTransaksiListModels(postTransaksiListModels);
        }
        postTransaksiModel.setIdUser(idUser);
        postTransaksiModel.setIdPay("1");
        postTransaksiModel.setIdSaltype(idSaltype);
        postTransaksiModel.setIdRefund("0");
        postTransaksiModel.setTypeTrans("penjualan");
        postTransaksiModel.setKetRefund("0");
        postTransaksiModel.setTotalTransHD(totalTransHD);
        postTransaksiModel.setIdTax(idTax);
        postTransaksiModel.setPayTransHD("300000");
        postTransaksiModel.setCashBackTransHD(String.valueOf(totalKembalian));

//        Map<String, String> params = new HashMap<>();
//        params.put("idtb", idtb);
//        params.put("idbusiness", idbusiness);
//        params.put("idcop", idcop);
//        params.put("idoutlet", idoutlet);
//        params.put("idctm", idctm);
//        params.put("noinv_transHD", noinv_transHD);
//        params.put("diskon", diskon);
//        params.put("status_transHD", statusBayar);
//        Map<String, Boolean> params2 = new HashMap<>();
//        for (int j=0;j<dataIdProduk.size();j++){
//            dataIdProdukList[j][0] = dataIdProduk.get(j);
//            dataIdProdukList[j][1] = dataNamaProduk.get(j);
//            dataIdProdukList[j][2] = dataIdVariant.get(j);
//            dataIdProdukList[j][3] = dataNamaVariant.get(j);
//            dataIdProdukList[j][4] = dataHargaVariant.get(j);
//            dataIdProdukList[j][5] = dataJumlah.get(j);
//
//            Log.i("Hasil",""+dataIdProdukList[j][0] + dataIdProdukList[j][2] + "0" + dataIdProdukList[j][5] + dataIdProdukList[j][4] + "0");
//            postTransaksiListModels.add(new PostTransaksiListModel(dataIdProdukList[j][0], dataIdProdukList[j][2],"0", dataIdProdukList[j][5], dataIdProdukList[j][4], "0"));
//            params2.put("produklist", postTransaksiListModels.add(new PostTransaksiListModel(dataIdProdukList[j][0], dataIdProdukList[j][2],"0", dataIdProdukList[j][5], dataIdProdukList[j][4], "0")));
//        }
//        params.put("iduser", idUser);
//        params.put("idpay", "1");
//        params.put("idsaltype", idSaltype);
//        params.put("idrefund", "0");
//        params.put("type_trans", "penjualan");
//        params.put("ket_refund", "0");
//        params.put("total_transHD", totalTransHD);
//        params.put("idtax", idTax);
//        params.put("pay_transHD", "300000");
//        params.put("cashback_transHD", String.valueOf(totalKembalian));

        Log.i("DATAPRODUK", " === "+postTransaksiListModels.toString());

        int pos = 0;
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.sendTransaksi(postTransaksiModel).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        String result = response.body().string();

                        JSONObject jsonResult = new JSONObject(result);
                        jsonResult.getString("success");
                        jsonResult.getString("message");

                        Toast.makeText(PembayaranActivity.this, " " + jsonResult.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e){
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

//        mApiInterface = APIUrl.getAPIService();
//        mApiInterface.sendTransaksi(params, params2, postTransaksiModel).enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//
//            }
//        });
    }
}
