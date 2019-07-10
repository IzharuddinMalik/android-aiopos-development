package com.ultra.pos.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ultra.pos.R;
import com.ultra.pos.activity.DetailRiwayatTransaksiActivity;
import com.ultra.pos.activity.DetailTransaksiTesimpanActivity;
import com.ultra.pos.model.TransaksiModel;
import com.ultra.pos.util.StylingUtils;

import java.util.List;

public class AdapterRiwayatTransaksi extends RecyclerView.Adapter<AdapterRiwayatTransaksi.RiwayatViewHolder> {
    private Context mCtx;
    private List<TransaksiModel> listRiwayat;
    StylingUtils stylingUtils;
    @Override
    public RiwayatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_list_riwayat_transaksi, parent, false);

        return new AdapterRiwayatTransaksi.RiwayatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RiwayatViewHolder holder, final int position) {
        final TransaksiModel transaksiModel = listRiwayat.get(position);
        holder.total.setText(transaksiModel.getTotal());
        holder.status.setText(transaksiModel.getStatus());
        holder.jam.setText(transaksiModel.getJam());

        if(transaksiModel.getTipepembayaran().equals("0")){
            holder.JenisPembayaran.setText("Tunai");
        }else {
            holder.JenisPembayaran.setText("EDC");
        }
    }

    @Override
    public int getItemCount() {
        return listRiwayat.size();
    }

    public class RiwayatViewHolder extends RecyclerView.ViewHolder{
        TextView total,status,jam,JenisPembayaran;
        public RiwayatViewHolder(View view) {
            super(view);

            total=view.findViewById(R.id.tvTotalTransaksi);
            status=view.findViewById(R.id.tvStatusTransaksi);
            jam=view.findViewById(R.id.tvJamTransaksi);
            JenisPembayaran=view.findViewById(R.id.tvJenisPembayaran);

            view.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    Intent intent = new Intent(view.getContext(), DetailRiwayatTransaksiActivity.class);
                    intent.putExtra("idtrans", listRiwayat.get(position).getIdtrans());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                }
            });

            stylingUtils.robotoRegularTextview(mCtx, total);
            stylingUtils.robotoRegularTextview(mCtx, status);
            stylingUtils.robotoRegularTextview(mCtx, jam);
        }
    }

    public AdapterRiwayatTransaksi(Context context, List<TransaksiModel> listRiwayat){
        this.mCtx = context;
        this.listRiwayat = listRiwayat;

        stylingUtils = new StylingUtils();
    }
}
