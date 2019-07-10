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
import com.ultra.pos.adapter.AdapterRingkasanOrder;
import com.ultra.pos.adapter.AdapterTransaksiTersimpan;
import com.ultra.pos.api.APIConnect;
import com.ultra.pos.api.APIUrl;
import com.ultra.pos.api.BaseApiInterface;
import com.ultra.pos.model.OrderModel;
import com.ultra.pos.model.PesananModel;
import com.ultra.pos.model.PostTransaksiListModel;
import com.ultra.pos.model.PostTransaksiModel;
import com.ultra.pos.model.TransaksiModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
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
    int totalKembalian=0;
    TextInputEditText jumlahLain;
    String totalbayar, idtb, idbusiness, idcop, idoutlet, idctm, noinv_transHD, diskon, statusBayar, idUser, idSaltype, totalTransHD;
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
        dataIdTax = bundle.getStringArrayList("1");


        Log.i("TRANSAKSI COBA", " === " +idtb +" " +idbusiness+" "+idcop+" "+idoutlet+" "+idctm+" "+noinv_transHD+" "+diskon+" "+statusBayar+" "+dataIdProduk +" "+dataNamaProduk+" "+dataIdVariant+" "+dataNamaVariant+" "+dataHargaVariant+" "+idUser+" "+idSaltype+" "+totalTransHD);


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
        String[][] dataIdProdukList = new String[dataIdProduk.size()][5];

        for (int j=0;j<dataIdProduk.size();j++){
            dataIdProdukList[j][0] = dataIdProduk.get(j);
            dataIdProdukList[j][1] = dataNamaProduk.get(j);
            dataIdProdukList[j][2] = dataIdVariant.get(j);
            dataIdProdukList[j][3] = dataNamaVariant.get(j);
            dataIdProdukList[j][4] = dataHargaVariant.get(j);

            Log.i("Hasil",""+dataIdProdukList[j][0]+" "+dataIdProdukList[j][1]+" "+dataIdProdukList[j][2]+" "+dataIdProdukList[j][3]);
        }

        postTransaksiListModels = new ArrayList<>();
        postTransaksiListModels.add(new PostTransaksiListModel(dataIdProduk, dataNamaProduk, dataIdVariant, dataNamaVariant, dataHargaVariant, dataIdTax));

        PostTransaksiModel postTransaksiModel = new PostTransaksiModel();
        postTransaksiModel.setIdTb(idtb);
        postTransaksiModel.setIdBusiness(idbusiness);
        postTransaksiModel.setIdCop(idcop);
        postTransaksiModel.setIdOutlet(idoutlet);
        postTransaksiModel.setIdCtm(idctm);
        postTransaksiModel.setNoInvTransHD(noinv_transHD);
        postTransaksiModel.setDiskonTransaksi(diskon);
        postTransaksiModel.setStatusTransHD(statusBayar);
        postTransaksiModel.setPostTransaksiListModels(postTransaksiListModels);
        postTransaksiModel.setIdUser(idUser);
        postTransaksiModel.setIdPay("1");
        postTransaksiModel.setIdSaltype("11");
        postTransaksiModel.setTypeTrans("penjualan");
        postTransaksiModel.setKetRefund("0");
        postTransaksiModel.setTotalTransHD(totalTransHD);
        postTransaksiModel.setIdTax("1");
        postTransaksiModel.setPayTransHD("100000");
        postTransaksiModel.setCashBackTransHD(String.valueOf(totalKembalian));

        Log.i("PRODUKLIST", " === " +postTransaksiListModels);

        mApiInterface = APIUrl.getAPIService();
        mApiInterface.sendTransaksi(postTransaksiModel).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
