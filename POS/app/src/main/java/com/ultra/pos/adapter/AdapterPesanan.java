package com.ultra.pos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ultra.pos.R;
import com.ultra.pos.model.Produk;
import com.ultra.pos.util.StylingUtils;

import java.util.List;

public class AdapterPesanan extends RecyclerView.Adapter<AdapterPesanan.PesananViewHolder>{
    private Context mCtx;
    private List<Produk> listPesanan;
    StylingUtils stylingUtils;

    @Override
    public PesananViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_detail_transaksi_tersimpan, parent, false);

        return new AdapterPesanan.PesananViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(final PesananViewHolder holder, final int position) {
        final Produk produkModel = listPesanan.get(position);
        holder.namaorder.setText(produkModel.getNamaProduk());
        holder.hargaorder.setText(produkModel.getHargaProduk());
        holder.jumlahorder.setText(produkModel.getJumlahProduk());
    }

    @Override
    public int getItemCount() {
        return listPesanan.size();
    }

    public class PesananViewHolder extends RecyclerView.ViewHolder {
        TextView jumlahorder,namaorder,hargaorder;
        public PesananViewHolder(View view) {
            super(view);

            jumlahorder = view.findViewById(R.id.tvJumlahDetailOrder);
            namaorder = view.findViewById(R.id.tvNamaDetailOrder);
            hargaorder = view.findViewById(R.id.tvHargaDetailOrder);

            stylingUtils.robotoRegularTextview(mCtx, jumlahorder);
            stylingUtils.robotoRegularTextview(mCtx, namaorder);
            stylingUtils.robotoRegularTextview(mCtx, hargaorder);
        }
    }

    public AdapterPesanan(Context context, List<Produk> listPesanan){
        this.mCtx = context;
        this.listPesanan = listPesanan;

        stylingUtils = new StylingUtils();
    }
}
