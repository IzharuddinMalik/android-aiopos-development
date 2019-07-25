package com.aio.pos.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aio.pos.R;
import com.aio.pos.activity.Dashboard;
import com.aio.pos.activity.DashboardFragment;
import com.squareup.picasso.Picasso;
import com.aio.pos.model.Produk;
import com.aio.pos.util.StylingUtils;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPilihProduk extends RecyclerView.Adapter<AdapterPilihProduk.ProdukViewHolder> implements Filterable {

    private Context mCtx;
    private ArrayList<Produk> listProduk;
    private ArrayList<Produk> mFilteredList;
    StylingUtils stylingUtils;
    ArrayList<String> arrjumlahPesanan= new ArrayList<String>();

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
                            filteredList.add(pos, new Produk(name.getIdProduk(), name.getNamaProduk(),name.getIdVariant(),name.getNamaVariant(),name.getFotoProduk(), name.getHargaProduk()));
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
        Button minus;
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
                                ((Dashboard)mCtx).setPesanan(mFilteredList.get(position).getIdProduk(),
                                        mFilteredList.get(position).getIdKategori(),
                                        "",
                                        "",
                                        mFilteredList.get(position).getNamaProduk(),
                                        mFilteredList.get(position).getHargaProduk(),
                                        jumlahPesanan.getText().toString());
                                ((Dashboard)mCtx).setOrder(mFilteredList.get(position).getIdProduk(),
                                        mFilteredList.get(position).getNamaProduk(),
                                        mFilteredList.get(position).getIdVariant(),
                                        mFilteredList.get(position).getNamaVariant(),
                                        mFilteredList.get(position).getHargaProduk(),
                                        jumlahPesanan.getText().toString());
                            } else{
                                ((Dashboard)mCtx).setPesanan(listProduk.get(position).getIdProduk(),
                                        listProduk.get(position).getIdKategori(),
                                        listProduk.get(position).getIdVariant(),
                                        listProduk.get(position).getNamaVariant(),
                                        listProduk.get(position).getNamaProduk(),
                                        listProduk.get(position).getHargaProduk(),
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

                            if (inputJumlahProduk.getText().toString().length() == 0){
                                inputJumlahProduk.setError("Isikan Jumlah Produk");
                                inputJumlahProduk.requestFocus();
                            }

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

//                plus.setOnClickListener(v -> {
//                    counter++;
//                    visible();
//                    inputJumlahProduk.setText(""+counter);
//
//                });

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
        holder.namaProduk.setText(mFilteredList.get(position).getNamaProduk()+mFilteredList.get(position).getNamaVariant());
        Log.i("Isi Holder",""+holder.namaProduk.getText());
        holder.hargaProduk.setText(mFilteredList.get(position).getHargaProduk());
        Picasso.with(mCtx).load("http://backoffice.aiopos.id/picture/produk/" + mFilteredList.get(position).getFotoProduk()).transform(new CircleTransform()).into(holder.gambarProduk);
    }

    class CircleTransform implements Transformation {

        boolean mCircleSeparator = false;

        public CircleTransform() {
        }

        public CircleTransform(boolean circleSeparator) {
            mCircleSeparator = circleSeparator;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
            Canvas canvas = new Canvas(bitmap);
            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);
            paint.setShader(shader);
            float r = size / 2f;
            canvas.drawCircle(r, r, r - 1, paint);
            // Make the thin border:
            Paint paintBorder = new Paint();
            paintBorder.setStyle(Paint.Style.STROKE);
            paintBorder.setColor(Color.argb(84,0,0,0));
            paintBorder.setAntiAlias(true);
            paintBorder.setStrokeWidth(1);
            canvas.drawCircle(r, r, r-1, paintBorder);

            // Optional separator for stacking:
            if (mCircleSeparator) {
                Paint paintBorderSeparator = new Paint();
                paintBorderSeparator.setStyle(Paint.Style.STROKE);
                paintBorderSeparator.setColor(Color.parseColor("#FFFFFF"));
                paintBorderSeparator.setAntiAlias(true);
                paintBorderSeparator.setStrokeWidth(4);
                canvas.drawCircle(r, r, r+1, paintBorderSeparator);
            }
            squaredBitmap.recycle();
            return bitmap;
        }


        @Override
        public String key() {
            return "circle";
        }
    }

    public Fragment getItem(int position) {
        return DashboardFragment.newInstance(position);
    }
}
