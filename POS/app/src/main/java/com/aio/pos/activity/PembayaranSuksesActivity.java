package com.aio.pos.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.aio.pos.util.PrinterCommands;
import com.aio.pos.util.UnicodeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranSuksesActivity extends AppCompatActivity implements Runnable{

    Button bayarselesai,btnconnectprint;
    TextView KembalianPembayaran,date;
    String kembalian,idtrans;

    private List<Produk> listPesanan;
    private SharedPrefManager pref;
    String mDeviceAddress;
    BaseApiInterface mApiInterface;
    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;
    String namaProduk, hargaProduk, jumlahProduk, subTotalHarga, totalHarga, kodeTransHD, tglTransHD, jamTransHD, metodePembayaran, namaPelanggan;
    String namaBT="";
    private String outlet;
    JSONArray arrHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran_sukses);

        bayarselesai=findViewById(R.id.btnSelesaiPembayaran);
        KembalianPembayaran=findViewById(R.id.tvKembalianPembayaran);
        btnconnectprint=findViewById(R.id.btnConnectPrint);
        kembalian = getIntent().getStringExtra("kembalian");
        KembalianPembayaran.setText("Rp. "+kembalian);

        idtrans = getIntent().getStringExtra("hasil");
        getAlldetailriwayat();

        bayarselesai.setOnClickListener(v -> {
            startActivity(new Intent(this,Dashboard.class));
            finish();
        });

        if(namaBT==""){
            btnconnectprint.setText("Connect Printer & Print");
        }

        btnconnectprint.setOnClickListener(v -> {
//            dialogPrint();
            if(btnconnectprint.getText()=="Connect Printer & Print"){
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null){
                    Toast.makeText(this, "Message1", Toast.LENGTH_SHORT).show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()){
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    } else {
                        ListPairedDevices();
                        Intent connectIntent = new Intent(PembayaranSuksesActivity.this, DeviceList.class);
                        startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                    }
                }
            }else{
                printData();
            }
        });
    }


    public void getAlldetailriwayat(){
        idtrans = getIntent().getStringExtra("hasil");
        Log.i("isi id trans",""+idtrans);

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
                        Log.i("isi id trans", "isi" + result);

                        if (array.toString().equals("[]")) {

                        } else {

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject objKategori = array.getJSONObject(i);
                                Log.i("Cek Isi", "" + objKategori);
                                outlet = objKategori.getString("name_outlet");

                                Log.i("Isi Nama Pay", "Isinya ---->" + objKategori.getString("nama_pay").length());

                                metodePembayaran = objKategori.getString("nama_pay");

                                namaPelanggan = objKategori.getString("nama_pelanggan");
                                kodeTransHD = objKategori.getString("kode_transHD");
                                tglTransHD = objKategori.getString("tgl");
                                jamTransHD = objKategori.getString("jam");
                                subTotalHarga = objKategori.getString("total_transHD");

                                arrHarga = objKategori.getJSONArray("harga");
//                                Log.i("Isi",""+arrHarga);
                                Log.i("Isi", "" + arrHarga.length());

                                for (int j = 0; j < arrHarga.length(); j++) {
                                    JSONObject objharga = arrHarga.getJSONObject(j);
                                    namaProduk = objharga.getString("nama_produk");
                                    hargaProduk = objharga.getString("harga_satuan");
                                    jumlahProduk = objharga.getString("qty");
                                    listPesanan.add(j, new Produk("", "" + namaProduk, "", "", "" + hargaProduk, "", "" + jumlahProduk, ""));
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error", "fuck");
            }
        });
    }

    @Override
    public void onBackPressed(){
        try{
            if (mBluetoothSocket != null){
                mBluetoothSocket.close();
            }
        } catch (Exception e){
            Log.e("TAG", "Exe", e);
        }

        setResult(RESULT_CANCELED);
        finish();
    }

    public void printData(){
        Thread t = new Thread(){
            @Override
            public void run() {
                try{
                    pref = new SharedPrefManager(PembayaranSuksesActivity.this);
                    HashMap<String, String> user = pref.getUserDetails();

                    OutputStream os = mBluetoothSocket.getOutputStream();

                    os.write(PrinterCommands.ESC_ALIGN_CENTER);
                    os.write((user.get(SharedPrefManager.NAMA_BISNIS) +"\n\n").getBytes());
                    os.write((user.get(SharedPrefManager.NAMA_OUTLET) + "\n\n").getBytes());
                    os.write((user.get(SharedPrefManager.ALAMAT_OUTLET) + "\n\n").getBytes());
                    os.write((user.get(SharedPrefManager.NAMA_USER) + "\n\n").getBytes());
                    os.write(" ----------------------------- \n\n".getBytes());

                    os.write(PrinterCommands.ESC_ALIGN_LEFT);
                    if (namaPelanggan.equals("null")){
                        os.write(("Nama Pelanggan : " + " - " + "\n").getBytes());
                    } else {
                        os.write(("Nama Pelanggan : " + namaPelanggan + "\n").getBytes());
                    }
                    os.write(("Kode Transaksi : " + kodeTransHD +"\n").getBytes());
                    if (metodePembayaran.equals("null")){
                        os.write(("Metode Pembayaran : " + " - " + "\n").getBytes());
                    }else {
                        os.write(("Metode Pembayaran : " + metodePembayaran + "\n").getBytes());
                    }
                    os.write(("Tanggal Transaksi : " + tglTransHD + "\n").getBytes());
                    os.write(("Jam Transaksi : " + jamTransHD + "\n\n").getBytes());
                    os.write(PrinterCommands.ESC_ALIGN_CENTER);
                    os.write(" ----------------------------- \n\n".getBytes());

                    os.write(PrinterCommands.ESC_ALIGN_LEFT);
                    int subtotalItem = Integer.parseInt(hargaProduk) * Integer.parseInt(jumlahProduk);
                    for (int k=0;k<listPesanan.size();k++){
                        os.write((listPesanan.get(k).getNamaProduk() + "\n").getBytes());
                        os.write((listPesanan.get(k).getHargaProduk() + " x " + listPesanan.get(k).getJumlahProduk() + " = " + subtotalItem + "\n\n").getBytes());
                    }

                    os.write(PrinterCommands.ESC_ALIGN_CENTER);
                    os.write(" ----------------------------- \n\n".getBytes());

                    os.write(PrinterCommands.ESC_ALIGN_LEFT);
                    os.write(("Total : " + subTotalHarga + "\n\n").getBytes());

                    os.write(PrinterCommands.ESC_ALIGN_CENTER);
                    os.write(("Terima Kasih Atas Kunjungannya" + "\n").getBytes());
                    os.write(("POS by " + "\n").getBytes());
                    os.write(("aiopos.id" + "\n\n").getBytes());

                    os.write(PrinterCommands.ESC_ENTER);

                    //setting height
                    int gs = 29;
                    os.write(intToByteArray(gs));
                    int h = 109;
                    os.write(intToByteArray(h));
                    int n = 162;
                    os.write(intToByteArray(n));

                    //setting width
                    int gs_width = 29;
                    os.write(intToByteArray(gs_width));
                    int w = 119;
                    os.write(intToByteArray(w));
                    int n_width = 2;
                    os.write(intToByteArray(n_width));
                } catch (Exception e){
                    Log.e("DETAILRIWAYAT", "Exe", e);
                }
            }
        };
        t.start();
    }

    public void onDestroy(){
        super.onDestroy();
        try{
            if (mBluetoothSocket != null){
                mBluetoothSocket.close();
            }
        }catch (Exception e){
            Log.e("TAG", "Exe", e);
        }
    }



    public void onActivityResult(int mRequestCode, int mResultCode, Intent mDataIntent){
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode){
            case REQUEST_CONNECT_DEVICE:
                if (mResultCode == Activity.RESULT_OK){
                    Bundle mExtra = mDataIntent.getExtras();
                    if (mExtra != null){
                        mDeviceAddress = mExtra.getString("DeviceAddress");
                        Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                        mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);
                        mBluetoothConnectProgressDialog = ProgressDialog.show(this, "Connecting....", mBluetoothDevice.getName()+ " : " + mBluetoothDevice.getAddress(), true, false);
                        Thread mBluetoothConnectThread = new Thread(this);
                        mBluetoothConnectThread.start();
                    }
                }
                break;

            case REQUEST_ENABLE_BT :
                if (mResultCode == Activity.RESULT_OK){
                    ListPairedDevices();
                    Intent connectIntent = new Intent(PembayaranSuksesActivity.this, DeviceList.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void run(){
        try{
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException){
            Log.d(TAG, "CouldNoConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket){
        try{
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex){
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(PembayaranSuksesActivity.this, "Device Connected", Toast.LENGTH_SHORT).show();
            namaBT=mBluetoothDevice.getName();
            btnconnectprint.setText("Print Transaksi Lagi");
            printData();
        }
    };

    public static byte intToByteArray(int value){
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++){
            System.out.println("Selva ["+k+"] = " + "0x" + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }



    private void ListPairedDevices(){
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter.getBondedDevices();
        if (mPairedDevices.size() > 0){
            for (BluetoothDevice mDevice : mPairedDevices){
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  " + mDevice.getAddress());
            }
        }
    }

}
