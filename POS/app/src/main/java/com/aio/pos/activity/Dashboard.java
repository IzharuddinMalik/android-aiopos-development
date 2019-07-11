package com.aio.pos.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.aio.pos.R;
import com.aio.pos.adapter.AdapterDashboardListOrder;
import com.aio.pos.adapter.AdapterTipePenjualan;
import com.aio.pos.adapter.TabAdapter;
import com.aio.pos.api.APIConnect;
import com.aio.pos.api.APIUrl;
import com.aio.pos.api.BaseApiInterface;
import com.aio.pos.api.SharedPrefManager;
import com.aio.pos.model.PesananModel;
import com.aio.pos.model.Produk;
import com.aio.pos.model.ProdukModel;
import com.aio.pos.model.TipeModel;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout tabLayout;
    TabAdapter adapter;
    ImageView ivKeranjang, ivOptionMenu, ivSearch, ivCancelDialogDiskon, ivCancelDialogNama;
    LinearLayout llDashboardBukaPelanggan,llDashboardLihatPesanan;
    SearchView svNamaProduk;
    int backpress;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    CardView cvDiskon, cvNama, cvBatalTransaksi, cvTipePenjualan, cvCatatanPesanan;
    EditText edtDialogDiskonNilai, edtDialogNamaNilai, edtDialogCatatan;
    TextView tvDashboardNavNama, tvDashboardDiskonSemua, tvDashboardNilaiDiskonSemua, tvDashboardCatatan, tvDashboardNilaiCatatanSemua, tvDashboardTotalHarga,
            tvDashboardSubTotalHarga, tvDashboardNilaiSubTotalHarga, tvDashboardNamaPelanggan, tvDashboardTipePenjualan, tvDashboardNilaiTipePenjualan;
    SharedPrefManager pref;
    BaseApiInterface mApiInterface;
    APIConnect apiConnect;
    String idbusiness, idtb, idoutlet, diskon, idUser, idSaltype, idSaltype2, idTax;
    DashboardFragment dashboardFragment;
    List<ProdukModel> produkModels;
    List<Produk> produk;
    List<PesananModel> pesananModels;
    int positiontab= 0;
    ArrayList<String> arridProduk= new ArrayList<String>();
    ArrayList<String> arrnamaProduk= new ArrayList<String>();
    ArrayList<String> arridVariant= new ArrayList<String>();
    ArrayList<String> arrnamaVariant= new ArrayList<String>();
    ArrayList<String> arrhargaPesanan= new ArrayList<String>();
    ArrayList<String> arrjumlahPesanan= new ArrayList<String>();
    int totalHarga, subTotalHarga, totalHargaBaru, diskonan, hargaTotalBaru;
    FrameLayout frameLayout;
    AdapterDashboardListOrder adapterPesan;
    RecyclerView recPesanan, recTipePenjualan;
    AdapterTipePenjualan adapterTipePenjualan;
    List<TipeModel> listTipe;
    TextView tvOKDialogCatatan;
    Button btnDashboardSimpanPesanan, btnDashboardBayarPesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorGray4d4d4d));
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        tvDashboardNavNama = header.findViewById(R.id.tvDashboardNamaAkun);
        pref = new SharedPrefManager(Dashboard.this);
        HashMap<String, String> user = pref.getUserDetails();
        String nama = user.get(SharedPrefManager.NAMA_USER);
        tvDashboardNavNama.setText(Html.fromHtml("<b>" + nama+ "</b>"));
        idbusiness = user.get(SharedPrefManager.ID_BUSINESS);
        idUser = user.get(SharedPrefManager.ID_USER);
        idTax = user.get(SharedPrefManager.ID_TAX);

        tabLayout = findViewById(R.id.tabs);
        frameLayout = findViewById(R.id.frame_layout);
        ivKeranjang = findViewById(R.id.ivDashboardKeranjang);
        llDashboardLihatPesanan=findViewById(R.id.llDashboardLihatPesanan);

        tvDashboardNilaiDiskonSemua = findViewById(R.id.tvDashboardNilaiDiskonSemua);
        tvDashboardNilaiCatatanSemua = findViewById(R.id.tvDashboardNilaiCatatanSemua);
        tvDashboardCatatan = findViewById(R.id.tvDashboardCatatanSemua);
        tvDashboardDiskonSemua = findViewById(R.id.tvDashboardDiskonSemua);

        tvDashboardNamaPelanggan = findViewById(R.id.tvDashboardBukaPelanggan);

//        if (!tvDashboardNamaPelanggan.equals(null)){
//            tvDashboardNamaPelanggan.setText(getIntent().getStringExtra("namaPelanggan"));
//        } else {
//            tvDashboardNamaPelanggan.setText("Nama Pelanggan");
//        }

        String namapelanggan=""+getIntent().getStringExtra("namaPelanggan");
        Log.i("Nama",namapelanggan);
        if (namapelanggan.equals("null")){

        } else {
            tvDashboardNamaPelanggan.setText(getIntent().getStringExtra("namaPelanggan"));
        }

        ivSearch = findViewById(R.id.ivDashboardGambarSearch);
        svNamaProduk = findViewById(R.id.svDashboardNamaProduk);

        recPesanan = findViewById(R.id.rvDashboardDaftarPesanan);

        ivKeranjang.setOnClickListener(v -> {
            startActivity(new Intent(this,TransaksiTersimpanActivity.class));
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", "" + width);
        Log.d("height", "" + height);

        if (width == 720 && height == 1280){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            llDashboardLihatPesanan.setOnClickListener(v -> {
//                startActivity(new Intent(this, RingkasanOrderActivity.class));
                Intent intent = new Intent(this, RingkasanOrderActivity.class);
                intent.putExtra("idProduk", arridProduk);
                intent.putExtra("namaProduk", arrnamaProduk);
                intent.putExtra("idVariant", arridVariant);
                intent.putExtra("namaVariant", arrnamaVariant);
                intent.putExtra("hargaPesanan", arrhargaPesanan);
                intent.putExtra("jumlahPesanan", arrjumlahPesanan);
                intent.putExtra("idctm", getIntent().getStringExtra("idctm"));

                startActivity(intent);
            });
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            ivOptionMenu = findViewById(R.id.ivDashboardMenuOptions);
            ivOptionMenu.setOnClickListener(v -> {
                dialogFormOptionsMenu();
            });

            btnDashboardBayarPesanan = findViewById(R.id.btnDashboardBayarPesanan);
            btnDashboardSimpanPesanan = findViewById(R.id.btnDashboardSimpanPesanan);

            btnDashboardBayarPesanan.setOnClickListener(view -> {
                bayar();
            });
        }

        llDashboardBukaPelanggan = findViewById(R.id.llDashboardBukaPelanggan);
        llDashboardBukaPelanggan.setOnClickListener(v -> {
            startActivity(new Intent(this, PelangganActivity.class));
        });
        getAllListProduk();

        pesananModels = new ArrayList<PesananModel>();
        pesananModels.clear();

        tvDashboardTotalHarga = findViewById(R.id.tvDashboardAngkaTotalHargaPesanan);
        tvDashboardSubTotalHarga = findViewById(R.id.tvDashboardSubTotalHargaPesanan);
        tvDashboardNilaiSubTotalHarga = findViewById(R.id.tvDashboardAngkaSubTotalHargaPesanan);
        tvDashboardTipePenjualan = findViewById(R.id.tvDashboardTipePenjualanPesanan);
        tvDashboardNilaiTipePenjualan = findViewById(R.id.tvDashboardNilaiTipePenjualanPesanan);

    }

    public void dialogFormOptionsMenu(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_option_menu, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        cvDiskon = dialogView.findViewById(R.id.cvDashboardDiskon);
        cvNama = dialogView.findViewById(R.id.cvDashboardNama);
        cvBatalTransaksi = dialogView.findViewById(R.id.cvDashboardBatalTransaksi);
        cvTipePenjualan = dialogView.findViewById(R.id.cvDashboardTipePenjualan);
        cvCatatanPesanan = dialogView.findViewById(R.id.cvDashboardCatatanTransaksi);

        cvDiskon.setOnClickListener(v -> {
            dialogMenuDiskon();
        });

        cvNama.setOnClickListener(v -> {
            dialogMenuNama();
        });

        cvBatalTransaksi.setOnClickListener(view -> {
            cancelTransaksi();
        });

        cvTipePenjualan.setOnClickListener(view -> {
            dialogTipePenjualan();
        });

        cvCatatanPesanan.setOnClickListener(view -> {
            dialogCatatan();
        });

        dialog.show();
    }

    public void dialogMenuNama(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_nama, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        ivCancelDialogNama = dialogView.findViewById(R.id.ivCancelDialogNama);

        edtDialogNamaNilai = dialogView.findViewById(R.id.edtDialogFOrmNilaiNama);
        ivCancelDialogNama.setOnClickListener(v -> {
            cancelTransaksi();
        });

        dialog.setPositiveButton("SIMPAN", (dialogInterface, i) -> {
            String nama = edtDialogNamaNilai.getText().toString();

            if (nama.isEmpty()){

            } else{
                tvDashboardNamaPelanggan.setText(nama);
            }
        });

        dialog.show();
    }

    public void dialogMenuDiskon(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_diskon,null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        ivCancelDialogDiskon = dialogView.findViewById(R.id.ivCancelDialogDiskon);
        ivCancelDialogDiskon.setOnClickListener(v -> {
            startActivity(new Intent(this, Dashboard.class));
            this.finish();
        });

        edtDialogDiskonNilai = dialogView.findViewById(R.id.edtDialogFormNilaiDiskon);

        dialog.setPositiveButton("SIMPAN", (dialogInterface, i) -> {
            String nilaiDiskon = edtDialogDiskonNilai.getText().toString();

            if (nilaiDiskon.isEmpty()){
                tvDashboardNilaiDiskonSemua.setText("");
                tvDashboardNilaiDiskonSemua.setVisibility(View.GONE);
                tvDashboardDiskonSemua.setVisibility(View.GONE);
            } else{
                tvDashboardNilaiDiskonSemua.setText(nilaiDiskon + "%");
                tvDashboardTotalHarga.setText(String.valueOf(totalHargaBaru));
                tvDashboardNilaiSubTotalHarga.setText(String.valueOf(subTotalHarga));
                tvDashboardNilaiDiskonSemua.setVisibility(View.VISIBLE);
                tvDashboardDiskonSemua.setVisibility(View.VISIBLE);
                subTotalHarga();
                totalHargaBaru(nilaiDiskon);
            }

            diskon = nilaiDiskon;
            Log.i("subtotal" , " == " +subTotalHarga);
            dialogInterface.dismiss();
        });

        dialog.show();
    }

    public void dialogTipePenjualan(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_tipe_penjualan, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        recTipePenjualan = dialogView.findViewById(R.id.rvDashboardTipePenjualan);

        getSalesType();

        dialog.show();
    }

    public void getSalesType(){
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

                        for (int i = 0; i<array.length(); i++){
                            JSONObject objKategori = array.getJSONObject(i);
                            idSaltype = objKategori.getString("idsaltype");
                            objKategori.getString("nama_saltype");

                            listTipe.add(i, new TipeModel(objKategori.getString("idsaltype"),objKategori.getString("nama_saltype")));
                        }

                        idSaltype2 = idSaltype;

                        adapterTipePenjualan = new AdapterTipePenjualan(Dashboard.this, listTipe);
                        final RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(Dashboard.this);
                        recTipePenjualan.setLayoutManager(mLayoutManager2);
                        recTipePenjualan.setItemAnimator(new DefaultItemAnimator());
                        recTipePenjualan.setItemViewCacheSize(listTipe.size());
                        recTipePenjualan.setDrawingCacheEnabled(true);
                        recTipePenjualan.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                        recTipePenjualan.setAdapter(adapterTipePenjualan);
                        LayoutAnimationController animation2 = AnimationUtils.loadLayoutAnimation(Dashboard.this, R.anim.animation_slide_from_right);
                        recTipePenjualan.setLayoutAnimation(animation2);
                        adapterTipePenjualan.notifyDataSetChanged();
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

    public void dialogCatatan(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_catatan, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        edtDialogCatatan = dialogView.findViewById(R.id.editText);

        tvOKDialogCatatan = dialogView.findViewById(R.id.textView41);
        dialog.setPositiveButton("OK", (dialogInterface, i) -> {
            String catatan = edtDialogCatatan.getText().toString();

            if (catatan.isEmpty()){

            } else{
                tvDashboardNilaiCatatanSemua.setText(catatan);
                tvDashboardCatatan.setVisibility(View.VISIBLE);
                tvDashboardNilaiCatatanSemua.setVisibility(View.VISIBLE);
            }

            dialogInterface.dismiss();
        });

        dialog.show();
    }

    public void getAllListProduk(){

        pref = new SharedPrefManager(Dashboard.this);
        HashMap<String, String> user = pref.getUserDetails();
        String idBusiness = user.get(SharedPrefManager.ID_BUSINESS);
        String idTb = user.get(SharedPrefManager.ID_TB);
        String idOutlet = user.get(SharedPrefManager.ID_OUTLET);
        idbusiness = idBusiness;
        idtb = idTb;
        idoutlet = idOutlet;

        mApiInterface = APIUrl.getAPIService();
        HashMap<String, String> params = new HashMap<>();
        params.put("idbusiness",idbusiness);
        params.put("idtb", idtb);
        params.put("idoutlet", idoutlet);
        mApiInterface.getProduk(params, idbusiness, idtb, idoutlet).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);
                        JSONArray array = jsonResult.getJSONArray("info");

                        produkModels = new ArrayList<ProdukModel>();
                        produkModels.clear();

                        int pos = 0;

                        adapter = new TabAdapter(getSupportFragmentManager());

                        if(array.toString().equals("[]")){

                        }else{
                            for(int i=0;i<array.length();i++){
                                JSONObject objisinya = array.getJSONObject(i);
                                produk = new ArrayList<Produk>();
                                produk.clear();

                                dashboardFragment = new DashboardFragment();

                                replacementFragment(dashboardFragment);
                                JSONArray arrayProduk = objisinya.getJSONArray("produk");
                                if(arrayProduk.length()==0){
                                    produkModels.add(pos, new ProdukModel(objisinya.getString("idkategori"), objisinya.getString("nama_kategori"), produk));
                                    pos++;

                                    tabLayout.addTab(tabLayout.newTab().setText(""+objisinya.getString("nama_kategori")));
                                }else{
                                    for (int j=0;j<arrayProduk.length();j++){
                                        JSONObject objprod = arrayProduk.getJSONObject(j);
                                        produk.add(j, new Produk(objprod.getString("idproduk"), objprod.getString("nama_produk"), objprod.getString("idvariant"), objprod.getString("nama_variant"), objprod.getString("harga_produk"), objprod.getString("foto_produk"),objprod.getString("idkategori")));
                                    }

                                    produkModels.add(pos, new ProdukModel(objisinya.getString("idkategori"), objisinya.getString("nama_kategori"), produk));
                                    pos++;

                                    tabLayout.addTab(tabLayout.newTab().setText("" + objisinya.getString("nama_kategori")));
                                }
                            }
                        }

                        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                Log.e("TESLAGU", "--"+tab.getPosition());
                                positiontab = tab.getPosition();
                                getProdukModels(tab.getPosition());
                                DashboardFragment dashboardFragment1 = new DashboardFragment();
                                replacementFragment(dashboardFragment1);
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {
                                getProdukModels(tab.getPosition());
                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });
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

    @Override
    public void onBackPressed() {
        backpress = (backpress + 1);
        Toast.makeText(this, "Tekan Tombol Sekali Lagi", Toast.LENGTH_SHORT).show();

        if (backpress > 1){
            finish();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_transaksi) {
            startActivity(new Intent(this, Dashboard.class));
            finish();
        } else if (id == R.id.nav_transhistory) {
            startActivity(new Intent(this,RiwayatTerakhirActivity.class));
            finish();
        } else if (id == R.id.nav_pengaturan) {
            startActivity(new Intent(this, PengaturanActivity.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public List<ProdukModel> getProdukModels(int postabfrag){

        List<ProdukModel> pro = new ArrayList<ProdukModel>();
        pro.clear();

        int a = 0;

        if(produkModels.size()==0){

        }else{
            ProdukModel produkberdasarkantabfrag = produkModels.get(postabfrag);
            String idkat = produkberdasarkantabfrag.getIdKategori();

            Log.e("IDKATEGORI", " = "+idkat);

            for(int i=0;i<produkModels.size();i++){
                ProdukModel prods = produkModels.get(i);
                if(prods.getIdKategori().equals(idkat)) {
                    pro.add(a, new ProdukModel(prods.getIdKategori(), prods.getNamaKategori(), prods.getDataProduk()));
                    a++;
                }
            }

        }

        return pro;
    }

    public void replacementFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    public String gettabpos (){
        return String.valueOf(positiontab);
    }

    public void setPesanan(String idProduk, String idKategori, String idVariant, String namaVariant, String namaPesanan, String hargaPesanan, String jumlahPesanan){
        Log.e("contoh add"," = " + idProduk);

        int pos = pesananModels.size();

        pesananModels.add(pos, new PesananModel(String.valueOf(pos), idProduk, idKategori, idVariant, namaVariant, namaPesanan, hargaPesanan, jumlahPesanan));

        adapterPesan = new AdapterDashboardListOrder(Dashboard.this, pesananModels);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Dashboard.this);
        recPesanan.setLayoutManager(mLayoutManager);
        recPesanan.setItemAnimator(new DefaultItemAnimator());
        recPesanan.setItemViewCacheSize(pesananModels.size());
        recPesanan.setDrawingCacheEnabled(true);
        recPesanan.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recPesanan.setAdapter(adapterPesan);
        adapterPesan.notifyDataSetChanged();
        totalHarga();
        subTotalHarga();
    }

    public void setOrder(String idProduk,String namaProduk, String idVariant, String namaVariant, String hargaPesanan,String jumlahPesanan){
        arridProduk.add(idProduk);
        arrnamaProduk.add(namaProduk);
        arridVariant.add(idVariant);
        arrnamaVariant.add(namaVariant);
        arrhargaPesanan.add(hargaPesanan);
        arrjumlahPesanan.add(jumlahPesanan);
    }

    public void setOrder(String idProduk,String namaProduk, String idVariant, String namaVariant, String hargaPesanan){
        arridProduk.add(idProduk);
        arrnamaProduk.add(namaProduk);
        arridVariant.add(idVariant);
        arrnamaVariant.add(namaVariant);
        arrhargaPesanan.add(hargaPesanan);
    }

    public void resetOrder(){
        arridProduk.clear();
        arrnamaProduk.clear();
        arridVariant.clear();
        arrnamaVariant.clear();
        arrhargaPesanan.clear();
        arrjumlahPesanan.clear();
    }

    public void subTotalHarga(){
        int hargaSubTotal = 0;
        for (int i=0; i< pesananModels.size();i++){
            PesananModel pes = pesananModels.get(i);

            hargaSubTotal = hargaSubTotal + (Integer.parseInt(pes.getHargaProduk())* Integer.parseInt(pes.getJumlahPesanan()));

        }
        Log.d("SUBTOTAL", " == " + hargaSubTotal);
        tvDashboardNilaiSubTotalHarga.setText(String.valueOf(hargaSubTotal));
        subTotalHarga = hargaSubTotal;
    }

    public void totalHarga(){
        int hargaTotal = 0;
        for (int i=0; i< pesananModels.size();i++){
            PesananModel pes = pesananModels.get(i);

            hargaTotal = hargaTotal + (Integer.parseInt(pes.getHargaProduk()) * Integer.parseInt(pes.getJumlahPesanan()));

        }
        Log.d("TOTALHARGA", " == " + hargaTotal);
        Log.d("DISKON TOTAL" , " == " + diskon);
        tvDashboardTotalHarga.setText(String.valueOf(hargaTotal));
        totalHarga = hargaTotal;
    }

    public void totalHargaBaru(String diskonBaru){
        diskonan = (Integer.parseInt(diskonBaru) * subTotalHarga)/100;

        hargaTotalBaru = subTotalHarga - diskonan;

        Log.d("SUBTOTAL BARU", " == " + subTotalHarga);
        Log.d("hargaTotalBaru", " == " + hargaTotalBaru);
        Log.d("DISKONAN" , " == " + diskonan);
        tvDashboardTotalHarga.setText(String.valueOf(hargaTotalBaru));
        totalHargaBaru = hargaTotalBaru;
    }

    public void cancelTransaksi(){
        pesananModels = new ArrayList<PesananModel>();
        pesananModels.clear();

        startActivity(new Intent(this, Dashboard.class));
        finish();
    }

    public void onTipePenjualan(String namaTipePenjualan){

        tvDashboardNilaiTipePenjualan.setText(namaTipePenjualan);
        tvDashboardNilaiTipePenjualan.setVisibility(View.VISIBLE);
        tvDashboardTipePenjualan.setVisibility(View.VISIBLE);
    }

    public void bayar(){

        Intent intent = new Intent(this, PembayaranActivity.class);
        intent.putExtra("idtb", idtb);
        intent.putExtra("idbusiness", idbusiness);
        intent.putExtra("idcop", "0");
        intent.putExtra("idoutlet", idoutlet);
        intent.putExtra("idctm", getIntent().getStringExtra("idctm"));
        intent.putExtra("noinv_transHD", "0");
        intent.putExtra("diskon", diskon);
        intent.putExtra("status_transHD", "1");
        intent.putExtra("idProduk", arridProduk);
        intent.putExtra("namaProduk", arrnamaProduk);
        intent.putExtra("idVariant", arridVariant);
        intent.putExtra("namaVariant", arrnamaVariant);
        intent.putExtra("hargaPesanan", arrhargaPesanan);
        intent.putExtra("jumlahPesanan", arrjumlahPesanan);
        intent.putExtra("iduser", idUser);
        intent.putExtra("idsaltype", idSaltype2);
        intent.putExtra("total_transHD", tvDashboardTotalHarga.getText().toString());
        intent.putExtra("idtax", idTax);
        startActivity(intent);
    }

}
