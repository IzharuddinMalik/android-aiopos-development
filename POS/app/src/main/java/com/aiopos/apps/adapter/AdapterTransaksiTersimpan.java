package com.aiopos.apps.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiopos.apps.R;
import com.aiopos.apps.activity.DetailTransaksiTesimpanActivity;
import com.aiopos.apps.model.TransaksiModel;
import com.aiopos.apps.util.StylingUtils;

import java.util.List;

public class AdapterTransaksiTersimpan extends RecyclerView.Adapter<AdapterTransaksiTersimpan.TransaksiViewHolder> {
    private Context mCtx;
    private List<TransaksiModel> listTransaksi;
    StylingUtils stylingUtils;

    @Override
    public TransaksiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_transaksi_tersimpan, parent, false);

        return new AdapterTransaksiTersimpan.TransaksiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TransaksiViewHolder holder, final int position) {
        final TransaksiModel transaksiModel = listTransaksi.get(position);
        holder.total.setText(transaksiModel.getTotal());
        holder.nomor.setText(transaksiModel.getNomor());
    }

    @Override
    public int getItemCount() {
        return listTransaksi.size();
    }

    public class TransaksiViewHolder extends RecyclerView.ViewHolder{
        TextView total,nomor;
        public TransaksiViewHolder(View view) {
            super(view);

            total=view.findViewById(R.id.tvTotalBayarTransaksi);
            nomor=view.findViewById(R.id.tvNomorTransaksi);

            view.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    Intent intent = new Intent(view.getContext(), DetailTransaksiTesimpanActivity.class);
                    intent.putExtra("totalharga", listTransaksi.get(position).getTotal());
                    intent.putExtra("notransaksi", listTransaksi.get(position).getNomor());
                    intent.putExtra("jamtransaksi", listTransaksi.get(position).getJam());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                }
            });

            stylingUtils.robotoRegularTextview(mCtx, total);
            stylingUtils.robotoRegularTextview(mCtx, nomor);
        }
    }

    public AdapterTransaksiTersimpan(Context context, List<TransaksiModel> listTransaksi){
        this.mCtx = context;
        this.listTransaksi = listTransaksi;

        stylingUtils = new StylingUtils();
    }
}
