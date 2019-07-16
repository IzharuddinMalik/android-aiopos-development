package com.aio.pos.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.hardware.camera2.params.BlackLevelPattern;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aio.pos.R;
import com.aio.pos.adapter.AdapterPesanan;
import com.aio.pos.api.APIUrl;
import com.aio.pos.api.BaseApiInterface;
import com.aio.pos.api.SharedPrefManager;
import com.aio.pos.model.Produk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRiwayatTransaksiActivity extends AppCompatActivity {

    TextView total,jam,pelanggan,outlet,metode,kodetrans,tanggal, konekBT;
    RecyclerView recPesanan;
    private List<Produk> listPesanan;
    private AdapterPesanan adapter;
    Button cetak,batal, printBT;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    private SharedPrefManager pref;
    private String idtrans;
    private BaseApiInterface mApiInterface;
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;
    String lblPrinterName;

    OutputStream outputStream;
    InputStream inputStream;
    Thread thread;
    private Format format;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat_transaksi);

        total = findViewById(R.id.tvTotalDetailRiwayat);
        jam = findViewById(R.id.tvJamTransaksi);
        tanggal = findViewById(R.id.tvTanggalTransaksi);
        pelanggan = findViewById(R.id.tvDetailNamaPelangganRiwayat);
        outlet= findViewById(R.id.tvDetailNamaOutletRiwayat);
        metode=findViewById(R.id.tvDetailMetodePembayaranRiwayat);
        kodetrans=findViewById(R.id.tvDetailKodeTransaksiRiwayat);

        recPesanan = findViewById(R.id.rvDetailRiwayat);
        getAlldetailriwayat();


        cetak=findViewById(R.id.btnCetakRiwayat);

        cetak.setOnClickListener(v -> {
            dialogPrint();
        });
    }

    public void back(View view){
        startActivity(new Intent(this, RiwayatTerakhirActivity.class));
    }

    public void dialogPrint(){
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_form_cetak,null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        konekBT = dialogView.findViewById(R.id.tvYaPrint);
        printBT = dialogView.findViewById(R.id.btnPrintData);

        konekBT.setOnClickListener(view -> {
            try{
                findBluetoothDevice();
                openBluetoothDevice();
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        printBT.setOnClickListener(view -> {
            try{
                printData();
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        dialog.show();
    }

    public void getAlldetailriwayat(){
        idtrans = getIntent().getStringExtra("idtrans");

        listPesanan = new ArrayList<>();
        listPesanan.clear();

        HashMap<String, String> params = new HashMap<>();
        params.put("idtransHD",idtrans);

        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getdetailRiwayat(params,idtrans).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);
                        JSONArray array = jsonResult.getJSONArray("info");

                        if(array.toString().equals("[]")){

                        }else{

                            for (int i = 0; i<array.length(); i++){
                                JSONObject objKategori = array.getJSONObject(i);
                                Log.i("Cek Isi",""+objKategori);
                                outlet.setText(objKategori.getString("name_outlet"));
                                metode.setText(objKategori.getString("nama_pay"));
                                kodetrans.setText(objKategori.getString("kode_transHD"));
                                tanggal.setText(objKategori.getString("tgl"));
                                jam.setText(objKategori.getString("jam"));
                                total.setText(objKategori.getString("total_transHD"));
                                pelanggan.setText(objKategori.getString("nama_pelanggan"));
//

                                JSONArray arrHarga = objKategori.getJSONArray("harga");
//                                Log.i("Isi",""+arrHarga);
                                Log.i("Isi",""+arrHarga.length());

                                for(int j=0;j<arrHarga.length();j++){
                                    JSONObject objharga = arrHarga.getJSONObject(j);
                                    listPesanan.add(j, new Produk("",""+objharga.getString("nama_produk"),"","",""+objharga.getString("harga_satuan"),"",""+objharga.getString("qty"),""));
                                }
                                adapter = new AdapterPesanan(DetailRiwayatTransaksiActivity.this, listPesanan);
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DetailRiwayatTransaksiActivity.this);
                                recPesanan.setLayoutManager(mLayoutManager);
                                recPesanan.setItemAnimator(new DefaultItemAnimator());
                                recPesanan.setItemViewCacheSize(listPesanan.size());
                                recPesanan.setDrawingCacheEnabled(true);
                                recPesanan.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                                recPesanan.setAdapter(adapter);
                                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(DetailRiwayatTransaksiActivity.this, R.anim.animation_slide_from_right);
                                recPesanan.setLayoutAnimation(animation);
                                adapter.notifyDataSetChanged();
                            }
                        }

//                        for (int i = 0; i<array.length(); i++){
//                            JSONObject objKategori = array.getJSONObject(i);
//
//                            listPesanan.add(i, new Produk("3", ""+objKategori.getString("nama_Produk"), "9000", "Aceh","","",""));
//                        }

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

    public void findBluetoothDevice(){
        try{

            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter == null){
                Toast.makeText(this, "No Bluetooth Adapter Found", Toast.LENGTH_LONG).show();
            }
            if (bluetoothAdapter.isEnabled()){
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBT, 0);
            }

            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();

            if (pairedDevice.size()>0){
                for (BluetoothDevice pairedDev:pairedDevice){

                    if (pairedDev.getName().equals("RPP02N")) {
                        bluetoothDevice = pairedDev;
                        Toast.makeText(this, "Bluetooth Printer Attached :" + pairedDev.getName(), Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }
            Toast.makeText(this, "Bluetooth Printer Attached", Toast.LENGTH_LONG).show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void openBluetoothDevice() throws IOException{
        try{

            // Standard UUID from String//
            UUID uuidString = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuidString);
            bluetoothSocket.connect();
            outputStream=bluetoothSocket.getOutputStream();
            inputStream = bluetoothSocket.getInputStream();

            beginListenData();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void beginListenData(){
        try{

            final Handler handler = new Handler();
            final byte delimiter = 10;
            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    while(!Thread.currentThread().isInterrupted() && !stopWorker){
                        try{
                            int byteAvailable = inputStream.available();
                            if (byteAvailable>0){
                                byte[] packetByte = new byte[byteAvailable];
                                inputStream.read(packetByte);

                                for (int i=0; i < byteAvailable; i++){
                                    byte b = packetByte[i];
                                    if (b==delimiter){
                                        byte[] encodedByte = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedByte, 0,
                                                encodedByte.length
                                        );
                                        final String data = new String(encodedByte, "US-ASCII");
                                        readBufferPosition=0;
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(DetailRiwayatTransaksiActivity.this, "" + data, Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    } else{
                                        readBuffer[readBufferPosition++]=b;
                                    }
                                }
                            }
                        } catch (Exception e){
                            stopWorker=true;
                        }
                    }
                }
            });

            thread.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printData() throws IOException{
        try{

            String BILL = " ";

            BILL = BILL + String.format("%1$-10s %2$10s %3$13s %4$10s", outlet.getText().toString());
            BILL = BILL + String.format("%1$-10s %2$10s %3$11s %4$10s", metode.getText().toString());

            outputStream.write(BILL.getBytes());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
