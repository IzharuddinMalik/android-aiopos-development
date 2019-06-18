package com.ultra.pos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        public ProdukViewHolder(View view){
            super(view);

            gambarProduk = view.findViewById(R.id.ivDashboardGambarProduk);
            namaProduk = view.findViewById(R.id.tvDashboardNamaProduk);
            hargaProduk = view.findViewById(R.id.tvDashboardHargaProduk);
            inputJumlahProduk = view.findViewById(R.id.edtDashboardJumlahProduk);

            stylingUtils.robotoRegularTextview(mCtx, namaProduk);
            stylingUtils.robotoRegularTextview(mCtx, hargaProduk);
            stylingUtils.robotoRegularEdittext(mCtx, inputJumlahProduk);
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
