package com.ultra.pos.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ultra.pos.R;
import com.ultra.pos.adapter.AdapterDashboardListOrder;
import com.ultra.pos.adapter.AdapterPilihProduk;
import com.ultra.pos.adapter.TabAdapter;
import com.ultra.pos.api.APIConnect;
import com.ultra.pos.api.APIUrl;
import com.ultra.pos.api.BaseApiInterface;
import com.ultra.pos.api.SharedPrefManager;
import com.ultra.pos.model.PesananModel;
import com.ultra.pos.model.Produk;
import com.ultra.pos.model.ProdukModel;

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
    CardView cvDiskon, cvNama, cvBatalTransaksi;
    EditText edtDialogDiskonNilai, edtDialogNamaNilai;
    Button btnSimpanDialogDiskon, btnSimpanDialogNama;
    TextView tvDashboardNavNama;
    SharedPrefManager pref;
    BaseApiInterface mApiInterface;
    APIConnect apiConnect;
    String idbusiness, idtb, idoutlet;
    DashboardFragment dashboardFragment;
    List<ProdukModel> produkModels;
    List<Produk> produk;
    List<PesananModel> pesananModels;
    int positiontab= 0;
    ArrayList<String> array= new ArrayList<String>();
    ArrayList<String> array2= new ArrayList<String>();
    ArrayList<String> array3= new ArrayList<String>();
    ArrayList<String> array4= new ArrayList<String>();
    ArrayList<String> array5= new ArrayList<String>();
    FrameLayout frameLayout;
    AdapterDashboardListOrder adapterPesan;
    RecyclerView recPesanan;

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


        tabLayout = findViewById(R.id.tabs);
        frameLayout = findViewById(R.id.frame_layout);
        ivKeranjang = findViewById(R.id.ivDashboardKeranjang);
        llDashboardLihatPesanan=findViewById(R.id.llDashboardLihatPesanan);

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
                intent.putExtra("array", array);
                intent.putExtra("array2", array2);
                intent.putExtra("array3", array3);
                intent.putExtra("array4", array4);
                intent.putExtra("array5", array5);

                startActivity(intent);
            });
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            ivOptionMenu = findViewById(R.id.ivDashboardMenuOptions);
            ivOptionMenu.setOnClickListener(v -> {
                dialogFormOptionsMenu();
            });
        }

        llDashboardBukaPelanggan = findViewById(R.id.llDashboardBukaPelanggan);
        llDashboardBukaPelanggan.setOnClickListener(v -> {
            startActivity(new Intent(this, PelangganActivity.class));
        });
        getAllListProduk();

        pesananModels = new ArrayList<PesananModel>();
        pesananModels.clear();

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

        cvDiskon.setOnClickListener(v -> {
            dialogMenuDiskon();
        });

        cvNama.setOnClickListener(v -> {
            dialogMenuNama();
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

        btnSimpanDialogDiskon = dialogView.findViewById(R.id.btnDialogFormSimpan);
        btnSimpanDialogDiskon.setOnClickListener(v -> {
            startActivity(new Intent(this, Dashboard.class));
            this.finish();
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
        ivCancelDialogNama.setOnClickListener(v -> {
            startActivity(new Intent(this, Dashboard.class));
            this.finish();
        });

        btnSimpanDialogNama = dialogView.findViewById(R.id.btnDialogFormSimpanNama);
        btnSimpanDialogNama.setOnClickListener(v -> {
            startActivity(new Intent(this, Dashboard.class));
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

//                                    adapter.addFragment(new DashboardFragment(), ""+objisinya.get("nama_kategori"));
                                    tabLayout.addTab(tabLayout.newTab().setText(""+objisinya.getString("nama_kategori")));
                                }else{
                                    for (int j=0;j<arrayProduk.length();j++){
                                        JSONObject objprod = arrayProduk.getJSONObject(j);
                                        produk.add(j, new Produk(objprod.getString("idproduk"), objprod.getString("nama_produk"), objprod.getString("idvariant"), objprod.getString("nama_variant"), objprod.getString("harga_produk"), objprod.getString("foto_produk"),objprod.getString("idkategori")));
                                    }

                                    produkModels.add(pos, new ProdukModel(objisinya.getString("idkategori"), objisinya.getString("nama_kategori"), produk));
                                    pos++;

//                                    adapter.addFragment(new DashboardFragment(), ""+objisinya.get("nama_kategori"));
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
        } else if (id == R.id.nav_shiftkerja) {
            startActivity(new Intent(this, ShiftActivity.class));
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

    public void setPesanan(String idProduk, String idKategori, String namaPesanan, String hargaPesanan){
        Log.e("contoh add"," = " + idProduk);

        int pos = pesananModels.size();

        pesananModels.add(pos, new PesananModel(String.valueOf(pos), idProduk, idKategori, namaPesanan, hargaPesanan));

        adapterPesan = new AdapterDashboardListOrder(Dashboard.this, pesananModels);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Dashboard.this);
        recPesanan.setLayoutManager(mLayoutManager);
        recPesanan.setItemAnimator(new DefaultItemAnimator());
        recPesanan.setItemViewCacheSize(pesananModels.size());
        recPesanan.setDrawingCacheEnabled(true);
        recPesanan.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recPesanan.setAdapter(adapterPesan);
        adapterPesan.notifyDataSetChanged();
    }

    public void setOrder(String idProduk, String idKategori, String namaPesanan, String hargaPesanan,String jumlahPesanan){
        array.add(idProduk);
        array2.add(idKategori);
        array3.add(namaPesanan);
        array4.add(hargaPesanan);
        array5.add(jumlahPesanan);
    }

    public void resetOrder(){
        array.clear();
        array2.clear();
        array3.clear();
        array4.clear();
        array5.clear();
    }

}
