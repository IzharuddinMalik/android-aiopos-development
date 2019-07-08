package com.ultra.pos.activity;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ultra.pos.R;
import com.ultra.pos.adapter.AdapterPesanan;
import com.ultra.pos.adapter.AdapterRingkasanOrder;
import com.ultra.pos.adapter.AdapterTipePenjualan;
import com.ultra.pos.model.OrderModel;
import com.ultra.pos.model.PesananModel;
import com.ultra.pos.model.Produk;
import com.ultra.pos.model.TipeModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RingkasanOrderActivity extends AppCompatActivity {
    RecyclerView recOrder,recTipe;
    Button simpan,bayar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextView TOTAL;
    ImageView ivKeranjang, ivOptionMenu, ivSearch, ivCancelDialogDiskon, ivCancelDialogNama;
    Button btnSimpanDialogDiskon, btnSimpanDialogNama;
    private List<Produk> listOrder;
    private List<TipeModel> listTipe;
    List<PesananModel> pesananModels;
    private AdapterPesanan adapter;
    private AdapterTipePenjualan adapter2;
    List<String> data=new ArrayList<String>();
    List<String> data2=new ArrayList<String>();
    List<String> data3=new ArrayList<String>();
    List<String> data4=new ArrayList<String>();
    List<String> data5=new ArrayList<String>();

    List<String> databaru=new ArrayList<String>();
    List<String> databaru2=new ArrayList<String>();
    List<String> databaru3=new ArrayList<String>();
    List<String> databaru4=new ArrayList<String>();
    List<String> databaru5=new ArrayList<String>();
    int total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringkasan_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        data = bundle.getStringArrayList("array");
        data2 = bundle.getStringArrayList("array2");
        data3 = bundle.getStringArrayList("array3");
        data4 = bundle.getStringArrayList("array4");
        data5 = bundle.getStringArrayList("array5");

        selectionData();

        recOrder = findViewById(R.id.rvRingkasanOrder);
        simpan=findViewById(R.id.btnSimpanOrder);
        bayar=findViewById(R.id.btnBayarOrder);
        listOrder = new ArrayList<>();
        listOrder.clear();

        for(int i=0;i<databaru.size();i++){
            Log.i("ID",data.get(i));
            listOrder.add(i, new Produk("", ""+databaru3.get(i), "",""+databaru.get(i),""+databaru4.get(i),""+databaru5.get(i),""));
            total=total + Integer.parseInt(databaru4.get(i)) * Integer.parseInt(databaru5.get(i));
       }

        TOTAL=findViewById(R.id.tvTotalOrder);
        TOTAL.setText(""+total);

//        listOrder.add(0, new Produk("", "", "",""+data,"","",""));

//        listOrder.add(0, new Produk("Nasi Goreng", "2", "15.000"));
//        listOrder.add(1, new Produk("Ayam Goreng", "1", "18.000"));
//        listOrder.add(2, new Produk("Es Teh", "3", "2000"));
//        setPesanan("1","Es Teh", "Ayam", "3");

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
        listTipe = new ArrayList<>();
        listTipe.clear();

        listTipe.add(0, new TipeModel("Go Food"));
        listTipe.add(1, new TipeModel("Dine In"));
        listTipe.add(2, new TipeModel("Take Away"));

        adapter2 = new AdapterTipePenjualan(this, listTipe);
        final RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        recTipe.setLayoutManager(mLayoutManager2);
        recTipe.setItemAnimator(new DefaultItemAnimator());
        recTipe.setItemViewCacheSize(listTipe.size());
        recTipe.setDrawingCacheEnabled(true);
        recTipe.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recTipe.setAdapter(adapter2);
        LayoutAnimationController animation2 = AnimationUtils.loadLayoutAnimation(this, R.anim.animation_slide_from_right);
        recTipe.setLayoutAnimation(animation2);
        adapter2.notifyDataSetChanged();

        simpan.setOnClickListener(v -> {
            startActivity(new Intent(this,TransaksiTersimpanActivity.class));
            finish();
        });

        bayar.setOnClickListener(v -> {
            startActivity(new Intent(this,PembayaranActivity.class));
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.diskon){
            dialogMenuDiskon();
        } else if (item.getItemId() == R.id.hapus_pesanan) {
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

        ivCancelDialogDiskon = dialogView.findViewById(R.id.ivCancelDialogDiskon);
        ivCancelDialogDiskon.setOnClickListener(v -> {

        });

        btnSimpanDialogDiskon = dialogView.findViewById(R.id.btnDialogFormSimpan);
        btnSimpanDialogDiskon.setOnClickListener(v -> {

        });

        dialog.show();
    }

    public void dialogMenuHapus(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_hapus,null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

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
        finish();
    }

    public void selectionData(){
        String tmp="";
        int jml=0;
        for(int i=0;i<data3.size();i++){
            if(tmp.equals(data3.get(i))){

            }else{
                if(jml<Integer.parseInt(data5.get(i))){
                    databaru.add(data.get(i));
                    databaru2.add(data2.get(i));
                    databaru3.add(data3.get(i));
                    databaru4.add(data4.get(i));
                    databaru5.add(data5.get(i));
                    tmp=data3.get(i);
                    jml=Integer.parseInt(data5.get(i));
                }
            }
        }

        Log.i("Data",""+databaru3.size());
    }
}
