package com.aio.pos.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.aio.pos.R;
import com.aio.pos.activity.DetailRiwayatTransaksiActivity;
import com.aio.pos.model.Produk;
import com.aio.pos.model.TransaksiModel;
import com.aio.pos.util.StylingUtils;

import java.util.ArrayList;
import java.util.List;

public class AdapterRiwayatTransaksi extends RecyclerView.Adapter<AdapterRiwayatTransaksi.RiwayatViewHolder> {
    private Context mCtx;
    private List<TransaksiModel> listRiwayat;
    private List<TransaksiModel> mFilteredList;
    StylingUtils stylingUtils;
    @Override
    public RiwayatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_list_riwayat_transaksi, parent, false);

        return new AdapterRiwayatTransaksi.RiwayatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RiwayatViewHolder holder, final int position) {
        final TransaksiModel transaksiModel = mFilteredList.get(position);
        holder.total.setText(transaksiModel.getTotal());
        holder.status.setText(transaksiModel.getStatus());
        holder.jam.setText(transaksiModel.getJam());

        if(mFilteredList.get(position).getTipepembayaran().equals("0")){
            holder.JenisPembayaran.setText("Tunai");
        }else {
            holder.JenisPembayaran.setText("EDC");
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = listRiwayat;
                } else {

                    ArrayList<TransaksiModel> filteredList = new ArrayList<TransaksiModel>();

                    int pos = 0;
                    for (TransaksiModel name : listRiwayat) {

                        if (name.getTipepembayaran().toLowerCase().contains(charString) || name.getTipepembayaran().toUpperCase().contains(charString)) {
                            filteredList.add(pos, new TransaksiModel(name.getIdtrans(), name.getTotal(),name.getNomor(),name.getStatus(),name.getJam(), name.getTipepembayaran()));
                            pos = pos+1;
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<TransaksiModel>) filterResults.values;
                notifyDataSetChanged();
                Log.i("HASIL FILTER"," == " + mFilteredList);
            }
        };
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
        this.mFilteredList = listRiwayat;

        stylingUtils = new StylingUtils();
    }
}
