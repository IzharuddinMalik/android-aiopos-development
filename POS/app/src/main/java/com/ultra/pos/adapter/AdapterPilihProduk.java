package com.ultra.pos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ultra.pos.R;
import com.ultra.pos.activity.DashboardFragment;
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

        public ProdukViewHolder(View view, View view2){
            super(view);

            gambarProduk = view.findViewById(R.id.ivDashboardGambarProduk);
            namaProduk = view.findViewById(R.id.tvDashboardNamaProduk);
            hargaProduk = view.findViewById(R.id.tvDashboardHargaProduk);
            inputJumlahProduk = view.findViewById(R.id.edtDashboardJumlahProduk);
            llDashboardLihatPesanan=view.findViewById(R.id.llDashboardLihatPesanan);
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
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_dashboard, parent, false);

        return new ProdukViewHolder(viewItem, view2);
    }

    public void onBindViewHolder(final ProdukViewHolder holder, final int position){
        final ProdukModel produkModel = listProduk.get(position);
        holder.namaProduk.setText(produkModel.getNamaProduk());
        holder.hargaProduk.setText(produkModel.getHargaProduk());
        Picasso.with(mCtx).load("" + produkModel.getGambarProduk()).into(holder.gambarProduk);
    }

    public int getItemCount(){ return listProduk.size();}
}
