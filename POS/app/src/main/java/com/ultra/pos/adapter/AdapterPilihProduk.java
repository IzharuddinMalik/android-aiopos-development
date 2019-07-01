package com.ultra.pos.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ultra.pos.R;
import com.ultra.pos.activity.Dashboard;
import com.ultra.pos.model.ProdukModel;
import com.ultra.pos.util.StylingUtils;

import java.util.List;

public class AdapterPilihProduk extends RecyclerView.Adapter<AdapterPilihProduk.ProdukViewHolder> {

    private Context mCtx;
    private List<ProdukModel> listProduk;
    StylingUtils stylingUtils;

    public class ProdukViewHolder extends RecyclerView.ViewHolder{
        ImageView gambarProduk;
        TextView namaProduk, hargaProduk;
        EditText inputJumlahProduk;
        Button plus,minus;
        private int counter = 0;
        LinearLayout llDashboardLihatPesanan;

        public ProdukViewHolder(View view){
            super(view);

            gambarProduk = view.findViewById(R.id.ivDashboardGambarProduk);
            namaProduk = view.findViewById(R.id.tvDashboardNamaProduk);
            hargaProduk = view.findViewById(R.id.tvDashboardHargaProduk);
            inputJumlahProduk = view.findViewById(R.id.edtDashboardJumlahProduk);
            llDashboardLihatPesanan=view.findViewById(R.id.llDashboardLihatPesanan);

            WindowManager manager = (WindowManager) mCtx.getSystemService(Context.WINDOW_SERVICE);

            Display display = manager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            Log.d("Width", "" + width);
            Log.d("height", "" + height);

            Activity a = new Activity();

            if (width >= 1920 && height >= 1200){
                a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                view.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(view.getContext(), Dashboard.class);
                        intent.putExtra("namaProduk", listProduk.get(position).getNamaProduk());
                        intent.putExtra("hargaProduk", listProduk.get(position).getHargaProduk());
                        intent.putExtra("gambarProduk", listProduk.get(position).getGambarProduk());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        view.getContext().startActivity(intent);
                    }
                });
            } else {
                plus=view.findViewById(R.id.plus);
                minus=view.findViewById(R.id.minus);

                inputJumlahProduk.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length()!=0){
                            counter=Integer.parseInt(""+s);
                            visible();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                plus.setOnClickListener(v -> {
                    counter++;
                    visible();
                    inputJumlahProduk.setText(""+counter);
                });

                minus.setOnClickListener(v -> {
                    counter--;
                    visible();
                    inputJumlahProduk.setText(""+counter);
                });


                a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

//            if(counter!=0 || inputJumlahProduk!=null){
//                llDashboardLihatPesanan.setVisibility(View.VISIBLE);
//            }else{
//                llDashboardLihatPesanan.setVisibility(View.GONE);
//            }



            stylingUtils.robotoRegularTextview(mCtx, namaProduk);
            stylingUtils.robotoRegularTextview(mCtx, hargaProduk);
        }

        public void visible(){
            if((counter==0) || (inputJumlahProduk==null)){
                minus.setVisibility(View.INVISIBLE);
            }else {
                minus.setVisibility(View.VISIBLE);
            }
        }
    }



    public AdapterPilihProduk(Context context, List<ProdukModel> listProduk){
        this.mCtx = context;
        this.listProduk = listProduk;

        stylingUtils = new StylingUtils();
    }

    public ProdukViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_list_fragment_dashboard, parent, false);

        return new ProdukViewHolder(viewItem);
    }

    public void onBindViewHolder(final ProdukViewHolder holder, final int position){
        final ProdukModel produkModel = listProduk.get(position);
        holder.namaProduk.setText(produkModel.getNamaProduk());
        holder.hargaProduk.setText(produkModel.getHargaProduk());
        Picasso.with(mCtx).load("" + produkModel.getGambarProduk()).into(holder.gambarProduk);
    }

    public int getItemCount(){ return listProduk.size();}
}
