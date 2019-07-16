package com.aio.pos.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aio.pos.R;
import com.aio.pos.activity.Dashboard;
import com.aio.pos.activity.DashboardFragment;
import com.squareup.picasso.Picasso;
import com.aio.pos.model.Produk;
import com.aio.pos.util.StylingUtils;

import java.util.ArrayList;
import java.util.List;

public class AdapterPilihProduk extends RecyclerView.Adapter<AdapterPilihProduk.ProdukViewHolder> implements Filterable {

    private Context mCtx;
    private ArrayList<Produk> listProduk;
    private ArrayList<Produk> mFilteredList;
    StylingUtils stylingUtils;

    public AdapterPilihProduk(Context context, ArrayList<Produk> listProduk){
        this.mCtx = context;
        this.listProduk = listProduk;
        this.mFilteredList = listProduk;

        stylingUtils = new StylingUtils();
    }

    public int getItemCount(){ return mFilteredList.size();}

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = listProduk;
                } else {

                    ArrayList<Produk> filteredList = new ArrayList<Produk>();

                    int pos = 0;
                    for (Produk name : listProduk) {

                        if (name.getNamaProduk().toLowerCase().contains(charString) || name.getNamaProduk().toUpperCase().contains(charString)) {
                            filteredList.add(pos, new Produk(name.getIdProduk(), name.getNamaProduk(),name.getIdVariant(),name.getNamaVariant(), name.getHargaProduk()));
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
                mFilteredList = (ArrayList<Produk>) filterResults.values;
                notifyDataSetChanged();
                Log.i("HASIL FILTER"," == " + mFilteredList);
            }
        };
    }

    public class ProdukViewHolder extends RecyclerView.ViewHolder{
        ImageView gambarProduk;
        TextView namaProduk, hargaProduk;
        EditText inputJumlahProduk, jumlahPesanan;
        Button plus,minus;
        private int counter = 0;
        LinearLayout llDashboardLihatPesanan;


        public ProdukViewHolder(View view){
            super(view);

            gambarProduk = view.findViewById(R.id.ivDashboardGambarProduk);
            namaProduk = view.findViewById(R.id.tvDashboardNamaProduk);
            hargaProduk = view.findViewById(R.id.tvDashboardHargaProduk);
            inputJumlahProduk = view.findViewById(R.id.edtDashboardJumlahProduk);
            llDashboardLihatPesanan=view.findViewById(R.id.llDashboardLihatPesanan);
            jumlahPesanan = view.findViewById(R.id.edtDashboardJumlahProdukPesanan);

            WindowManager manager = (WindowManager) mCtx.getSystemService(Context.WINDOW_SERVICE);

            Display display = manager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            Log.d("Width", "" + width);
            Log.d("height", "" + height);

            Activity a = new Activity();
            if (width >= 1920 && height >= 1200){
//                a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                view.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        if (jumlahPesanan.getText().toString().length() == 0){
                            jumlahPesanan.setError("Isikan jumlah produk");
                            jumlahPesanan.requestFocus();
                        }else{
                            if (mFilteredList != listProduk){
                                ((Dashboard)mCtx).setPesanan(mFilteredList.get(position).getIdProduk(), mFilteredList.get(position).getIdKategori(),
                                        "", "", mFilteredList.get(position).getNamaProduk(),
                                        mFilteredList.get(position).getHargaProduk(),
                                        jumlahPesanan.getText().toString());
                                ((Dashboard)mCtx).setOrder(mFilteredList.get(position).getIdProduk(),
                                        mFilteredList.get(position).getNamaProduk(), mFilteredList.get(position).getIdVariant(),
                                        mFilteredList.get(position).getNamaVariant(),
                                        mFilteredList.get(position).getHargaProduk(),
                                        jumlahPesanan.getText().toString());
                            } else{
                                ((Dashboard)mCtx).setPesanan(listProduk.get(position).getIdProduk(), listProduk.get(position).getIdKategori(),
                                        listProduk.get(position).getIdVariant(), listProduk.get(position).getNamaVariant(),
                                        listProduk.get(position).getNamaProduk(), listProduk.get(position).getHargaProduk(),
                                        jumlahPesanan.getText().toString());
                                ((Dashboard)mCtx).setOrder(listProduk.get(position).getIdProduk(),
                                        listProduk.get(position).getNamaProduk(),
                                        listProduk.get(position).getIdVariant(),
                                        listProduk.get(position).getNamaVariant(),
                                        listProduk.get(position).getHargaProduk(),
                                        jumlahPesanan.getText().toString());
                            }
                        }
                    }
                });

            } else {
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

                            int position = getAdapterPosition();
                            Log.i("Posisi",listProduk.get(position).getNamaProduk()+" "+inputJumlahProduk.getText()+position);
                            if (position != RecyclerView.NO_POSITION){
                                if (mFilteredList != listProduk){
                                    ((Dashboard)mCtx).setOrder(mFilteredList.get(position).getIdProduk(),
                                            mFilteredList.get(position).getNamaProduk(),
                                            mFilteredList.get(position).getIdVariant(),
                                            mFilteredList.get(position).getNamaVariant(),
                                            mFilteredList.get(position).getHargaProduk(),
                                            inputJumlahProduk.getText().toString());
                                } else{
                                    ((Dashboard)mCtx).setOrder(listProduk.get(position).getIdProduk(),
                                            listProduk.get(position).getNamaProduk(),
                                            listProduk.get(position).getIdVariant(),
                                            listProduk.get(position).getNamaVariant(),
                                            listProduk.get(position).getHargaProduk(),
                                            inputJumlahProduk.getText().toString());
                                }
                            }
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
                    counter=0;
                    visible();
                    ((Dashboard)mCtx).resetOrder();
                    inputJumlahProduk.setText(null);
                });

                a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

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


    public ProdukViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_list_fragment_dashboard, parent, false);

        return new ProdukViewHolder(viewItem);
    }

    public void onBindViewHolder(final ProdukViewHolder holder, final int position){

//        if (listProduk == mFilteredList){
//            holder.namaProduk.setText(mProduk.getNamaProduk());
//            holder.hargaProduk.setText(mProduk.getHargaProduk());
//            Picasso.with(mCtx).load("http://backoffice.aiopos.id/picture/produk/" + mFilteredList.get(position).getFotoProduk()).into(holder.gambarProduk);
//        } else {
//            if (listProduk.get(position).getIdVariant().equals("")){
//                holder.namaProduk.setText(produk.getNamaProduk());
//                holder.hargaProduk.setText(produk.getHargaProduk());
//            } else{
//                holder.namaProduk.setText(produk.getNamaVariant());
//                holder.hargaProduk.setText(produk.getHargaProduk());
//            }
//            Picasso.with(mCtx).load("http://backoffice.aiopos.id/picture/produk/" + listProduk.get(position).getFotoProduk()).into(holder.gambarProduk);
//        }
        holder.namaProduk.setText(mFilteredList.get(position).getNamaProduk());
        Log.i("Isi Holder",""+holder.namaProduk.getText());
        holder.hargaProduk.setText(mFilteredList.get(position).getHargaProduk());
        Picasso.with(mCtx).load("http://backoffice.aiopos.id/picture/produk/" + mFilteredList.get(position).getFotoProduk()).into(holder.gambarProduk);
    }

    public Fragment getItem(int position) {
        return DashboardFragment.newInstance(position);
    }
}
