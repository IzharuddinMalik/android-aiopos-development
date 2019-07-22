package com.aiopos.apps.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiopos.apps.R;
import com.aiopos.apps.model.OrderModel;
import com.aiopos.apps.util.StylingUtils;

import java.util.List;

public class AdapterRingkasanOrder extends RecyclerView.Adapter<AdapterRingkasanOrder.OrderViewHolder> {
    private Context mCtx;
    private List<OrderModel> listOrder;
    StylingUtils stylingUtils;
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_list_order, parent, false);

        return new AdapterRingkasanOrder.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, final int position) {
        final OrderModel historyModel = listOrder.get(position);
        holder.nama.setText(historyModel.getNama());
        holder.jumlah.setText(historyModel.getJumlah());
        holder.harga.setText(historyModel.getHarga());
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView jumlah,nama,harga;
        public OrderViewHolder(View view){
            super(view);

            nama=view.findViewById(R.id.tvNamaItemOrder);
            jumlah=view.findViewById(R.id.tvJumlahItemOrder);
            harga=view.findViewById(R.id.tvHargaItemOrder);

            stylingUtils.robotoRegularTextview(mCtx, nama);
            stylingUtils.robotoRegularTextview(mCtx, jumlah);
            stylingUtils.robotoRegularTextview(mCtx, harga);
        }
    }

    public AdapterRingkasanOrder(Context context, List<OrderModel> listOrder){
        this.mCtx = context;
        this.listOrder = listOrder;

        stylingUtils = new StylingUtils();
    }
}
