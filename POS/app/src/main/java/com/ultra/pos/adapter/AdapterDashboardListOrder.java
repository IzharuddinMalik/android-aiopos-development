package com.ultra.pos.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ultra.pos.R;
import com.ultra.pos.activity.Dashboard;
import com.ultra.pos.model.PesananModel;
import com.ultra.pos.model.Produk;
import com.ultra.pos.util.StylingUtils;

import java.util.List;

public class AdapterDashboardListOrder extends RecyclerView.Adapter<AdapterDashboardListOrder.ListOrderViewHolder> {

    private Context mCtx;
    List<PesananModel> listOrderProduk;
    StylingUtils stylingUtils;
    AlertDialog.Builder dialog;
    View dialogView;
    EditText edtPopupNilaiJumlah, edtPopupNilaiDiskon, edtPopupNilaiCatatan;
    Button btnPopupKembali, btnPopupSimpan, btnPopupHapus;
    TextView tvPopupNamaOrder;

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

            int position = 0;
            view.setOnClickListener(v -> {
                Intent intent = new Intent(mCtx, AdapterDashboardListOrder.class);
                intent.putExtra("namaProduk", listOrderProduk.get(position).getNamaProduk());
                intent.putExtra("namaVariant", listOrderProduk.get(position).getNamaVariant());
                intent.putExtra("idVariant", listOrderProduk.get(position).getIdVariant());
                intent.putExtra("hargaProduk", listOrderProduk.get(position).getHargaProduk());
                dialogOrder(view);
            });

            stylingUtils.robotoRegularTextview(mCtx, tvNamaPesananProduk);
            stylingUtils.robotoRegularTextview(mCtx, tvHargaPesananProduk);
        }
    }

    public void dialogOrder(View view){
        dialog = new AlertDialog.Builder(AdapterDashboardListOrder.this.mCtx);
        dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_ringkasan_order, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        int pos = 0;

        tvPopupNamaOrder = view.findViewById(R.id.tvPopupNamaOrder);
        edtPopupNilaiJumlah = view.findViewById(R.id.edtPopupJumlahOrder);
        edtPopupNilaiDiskon = view.findViewById(R.id.edtPopupDiskonOrder);
        edtPopupNilaiCatatan = view.findViewById(R.id.edtPopupCatatanOrder);
        btnPopupKembali = view.findViewById(R.id.btnPopupKembali);
        btnPopupSimpan = view.findViewById(R.id.btnPopupSimpanOrder);
        btnPopupHapus = view.findViewById(R.id.btnPopupHapusOrder);

        if (listOrderProduk.get(pos).getIdVariant().equals("0")){
            tvPopupNamaOrder.setText(listOrderProduk.get(pos).getNamaProduk());
        } else{
            tvPopupNamaOrder.setText(listOrderProduk.get(pos).getNamaVariant());
        }



        btnPopupKembali.setOnClickListener(v -> {
            dialog.setCancelable(true);
        });

        btnPopupSimpan.setOnClickListener(v -> {
            Intent intent = new Intent(mCtx, Dashboard.class);

        });

        dialog.show();
    }

    public ListOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_daftar_pesanan, parent, false);

        return new ListOrderViewHolder(viewItem);
    }

    public void onBindViewHolder(final ListOrderViewHolder holder, int position){
        final PesananModel produk = listOrderProduk.get(position);
        if (produk.getIdVariant().equals("0")) {
            holder.tvNamaPesananProduk.setText(produk.getNamaProduk());
            holder.tvHargaPesananProduk.setText(produk.getHargaProduk());
        } else {
            holder.tvNamaPesananProduk.setText(produk.getNamaVariant());
            holder.tvHargaPesananProduk.setText(produk.getHargaProduk());
        }
    }

    public int getItemCount(){ return listOrderProduk.size();}
}
