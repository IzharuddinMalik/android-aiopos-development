package com.ultra.pos.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
    TextView tvPopupNamaOrder, tvPopupNilaiDiskon, tvPopupNilaiCatatan, tvPopupCatatan, tvPopupDiskon, tvPopupHargaOrder;
    int pos;

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
            tvPopupDiskon = view.findViewById(R.id.tvDashboardDaftarPesananDiskon);
            tvPopupCatatan = view.findViewById(R.id.tvDashboardDaftarPesananCatatan);
            tvPopupNilaiDiskon = view.findViewById(R.id.tvDashboardDaftarPesananNilaiDiskon);
            tvPopupNilaiCatatan = view.findViewById(R.id.tvDashboardDaftarPesananNilaiCatatan);

            stylingUtils.robotoRegularTextview(mCtx, tvNamaPesananProduk);
            stylingUtils.robotoRegularTextview(mCtx, tvHargaPesananProduk);
        }
    }

    public void dialogOrder(View view){
        dialog = new AlertDialog.Builder(AdapterDashboardListOrder.this.mCtx);
        dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_ringkasan_order, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        tvPopupNamaOrder = dialogView.findViewById(R.id.tvPopupNamaOrder);
        edtPopupNilaiJumlah = dialogView.findViewById(R.id.edtPopupJumlahOrder);
        edtPopupNilaiDiskon = dialogView.findViewById(R.id.edtPopupDiskonOrder);
        edtPopupNilaiCatatan = dialogView.findViewById(R.id.edtPopupCatatanOrder);
        btnPopupKembali = dialogView.findViewById(R.id.btnPopupKembali);
        btnPopupSimpan = dialogView.findViewById(R.id.btnPopupSimpanOrder);
        btnPopupHapus = dialogView.findViewById(R.id.btnPopupHapusOrder);
        tvPopupHargaOrder = dialogView.findViewById(R.id.tvPopupNilaiHargaOrder);

        final PesananModel pesananModel = listOrderProduk.get(pos);

        if (pesananModel.getIdVariant().equals("0")){
            tvPopupNamaOrder.setText(pesananModel.getNamaProduk());
        } else{
            tvPopupNamaOrder.setText(pesananModel.getNamaVariant());
        }

        tvPopupHargaOrder.setText(pesananModel.getHargaProduk());

        String catatan = edtPopupNilaiCatatan.getText().toString();
        String diskon = edtPopupNilaiDiskon.getText().toString();

        dialog.setPositiveButton("SIMPAN", (dialogInterface, i) -> {
            tvPopupNilaiDiskon.setText(diskon);
            tvPopupNilaiCatatan.setText(catatan);
            tvPopupNilaiDiskon.setVisibility(View.VISIBLE);
            tvPopupNilaiCatatan.setVisibility(View.VISIBLE);
            tvPopupDiskon.setVisibility(View.VISIBLE);
            tvPopupCatatan.setVisibility(View.VISIBLE);
            dialogInterface.dismiss();
        });

        dialog.setNegativeButton("BATAL", (dialogInterface, i) -> {
            dialogInterface.cancel();
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
