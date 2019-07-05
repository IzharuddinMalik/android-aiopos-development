package com.ultra.pos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ultra.pos.R;
import com.ultra.pos.model.PesananModel;
import com.ultra.pos.model.Produk;
import com.ultra.pos.util.StylingUtils;

import java.util.List;

public class AdapterDashboardListOrder extends RecyclerView.Adapter<AdapterDashboardListOrder.ListOrderViewHolder> {

    private Context mCtx;
    List<PesananModel> listOrderProduk;
    StylingUtils stylingUtils;

    public AdapterDashboardListOrder(Context context, List<PesananModel> listOrderProduk){
        this.mCtx = context;
        this.listOrderProduk = listOrderProduk;

        stylingUtils = new StylingUtils();
    }

    public class ListOrderViewHolder extends RecyclerView.ViewHolder{
        ImageView ivGambarPesananProduk;
        TextView tvNamaPesananProduk, tvHargaPesananProduk;

        public ListOrderViewHolder(View view){
            super(view);

            ivGambarPesananProduk = view.findViewById(R.id.ivDashboardDaftarPesananGambarProduk);
            tvNamaPesananProduk = view.findViewById(R.id.tvDashboardDaftarPesananNamaProduk);
            tvHargaPesananProduk = view.findViewById(R.id.tvDashboardDaftarPesananHargaProduk);

            stylingUtils.robotoRegularTextview(mCtx, tvNamaPesananProduk);
            stylingUtils.robotoRegularTextview(mCtx, tvHargaPesananProduk);
        }
    }

    public ListOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_daftar_pesanan, parent, false);

        return new ListOrderViewHolder(viewItem);
    }

    public void onBindViewHolder(final ListOrderViewHolder holder, int position){
        final PesananModel produk = listOrderProduk.get(position);
        holder.tvNamaPesananProduk.setText(produk.getNamaProduk());
        holder.tvHargaPesananProduk.setText(produk.getHargaProduk());
    }

    public int getItemCount(){ return listOrderProduk.size();}
}
