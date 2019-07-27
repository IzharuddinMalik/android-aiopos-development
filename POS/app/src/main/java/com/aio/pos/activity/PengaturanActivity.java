package com.aio.pos.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aio.pos.R;
import com.aio.pos.api.SharedPrefManager;
import com.aio.pos.util.UnicodeFormatter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class PengaturanActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Runnable{

    LinearLayout llProfilAkun,llPrinter,llBantuan;
    Button logout;
    SharedPrefManager pref;
    TextView tvDashboardNavNama, tvDashboardNavNamaBisnis, tvDashboardLocOutlet;
    ImageView ivlogoBisnis;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;
    protected static final String TAG = "TAG";
    String namaBT="";
    String mDeviceAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);
        Toolbar toolbar = findViewById(R.id.pengaturan_toolbar);
        setSupportActionBar(toolbar);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", "" + width);
        Log.d("height", "" + height);

        if (getResources().getConfiguration().smallestScreenWidthDp == 360){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_pengaturan_layout);
        NavigationView navigationView = findViewById(R.id.nav_pengaturan_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorGray4d4d4d));
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        tvDashboardNavNama = header.findViewById(R.id.tvDashboardNamaAkun);
        tvDashboardNavNamaBisnis = header.findViewById(R.id.tvDashboardNamaBisnis);
        tvDashboardLocOutlet = header.findViewById(R.id.tvLokasiOutlet);
        ivlogoBisnis = header.findViewById(R.id.ivLogoBisnis);
        pref = new SharedPrefManager(this);
        HashMap<String, String> user = pref.getUserDetails();
        String nama = user.get(SharedPrefManager.NAMA_USER);
        tvDashboardNavNama.setText(Html.fromHtml("<b>" + nama+ "</b>"));
        String bisnis = user.get(SharedPrefManager.NAMA_BISNIS);
        String locOutlet = user.get(SharedPrefManager.ALAMAT_OUTLET);
        tvDashboardNavNamaBisnis.setText(Html.fromHtml("<b>" + bisnis + "</b>"));
        tvDashboardLocOutlet.setText(Html.fromHtml("<b>" + locOutlet + "</b>"));
        Picasso.with(PengaturanActivity.this).load("http://backoffice.aiopos.id/picture/" + user.get(SharedPrefManager.IMG_BUSINESS)).into(ivlogoBisnis);

        llProfilAkun=findViewById(R.id.llProfilAkun);
        llPrinter=findViewById(R.id.llPrinter);
        llBantuan=findViewById(R.id.llBantuan);
        logout=findViewById(R.id.btnLogout);

        pref=new SharedPrefManager(this);
        logout.setOnClickListener(v -> {
            pref.logout();
            finish();
        });

        llProfilAkun.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfilActivity.class));
        });

        llPrinter.setOnClickListener(v -> {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null){
                Toast.makeText(this, "Message1", Toast.LENGTH_SHORT).show();
            } else {
                if (!mBluetoothAdapter.isEnabled()){
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                } else {
                    ListPairedDevices();
                    Intent connectIntent = new Intent(PengaturanActivity.this, PrinterActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                }
            }
        });

        llBantuan.setOnClickListener(v -> {
            startActivity(new Intent(this, BantuanActivity.class));
        });

        llBantuan.setEnabled(false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_transaksi) {
            startActivity(new Intent(this, Dashboard.class));
            finish();
        } else if (id == R.id.nav_transhistory) {
            startActivity(new Intent(this, RiwayatTerakhirActivity.class));
            finish();
        } else if (id == R.id.nav_pengaturan) {
            startActivity(new Intent(this, PengaturanActivity.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_pengaturan_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                    Intent connectIntent = new Intent(PengaturanActivity.this, PrinterActivity.class);
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
            Toast.makeText(PengaturanActivity.this, "Device Connected", Toast.LENGTH_SHORT).show();
            namaBT=mBluetoothDevice.getName();
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

    public byte[] sel(int val){
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putInt(val);
        buffer.flip();
        return buffer.array();
    }
}
