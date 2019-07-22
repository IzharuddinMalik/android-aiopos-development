package com.aiopos.apps.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiopos.apps.R;
import com.aiopos.apps.activity.Dashboard;
import com.aiopos.apps.activity.RingkasanOrderActivity;
import com.aiopos.apps.model.TipeModel;
import com.aiopos.apps.util.StylingUtils;

import java.util.List;

public class AdapterTipePenjualan extends RecyclerView.Adapter<AdapterTipePenjualan.TipeViewHolder> {

    private Context mCtx;
    private List<TipeModel> listTipe;
    StylingUtils stylingUtils;
    int index=-1;

    public class TipeViewHolder extends RecyclerView.ViewHolder{
        TextView tipe;
        LinearLayout itemRow;
        public TipeViewHolder(View view){
            super(view);

            tipe=view.findViewById(R.id.tvTipePenjualan);
            itemRow=view.findViewById(R.id.llTipePenjualan);
            stylingUtils.robotoRegularTextview(mCtx, tipe);


            WindowManager manager = (WindowManager) mCtx.getSystemService(Context.WINDOW_SERVICE);

            Display display = manager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            Log.d("Width", "" + width);
            Log.d("height", "" + height);
            if (width >= 1920 && height >= 1200){
                view.setOnClickListener(v -> {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        ((Dashboard)mCtx).onTipePenjualan(listTipe.get(pos).getTipe());
                    }
                });
            } else{

            }
        }
    }

    public AdapterTipePenjualan(Context context, List<TipeModel> listTipe){
        this.mCtx = context;
        this.listTipe = listTipe;

        stylingUtils = new StylingUtils();
    }

    @Override
    public TipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_list_tipepenjualan, parent, false);

        return new AdapterTipePenjualan.TipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TipeViewHolder holder, final int position) {
        WindowManager manager = (WindowManager) mCtx.getSystemService(Context.WINDOW_SERVICE);

        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", "" + width);
        Log.d("height", "" + height);

        if (width >= 1920 && height >= 1200){
            final TipeModel tipeModel = listTipe.get(position);
            holder.tipe.setText(tipeModel.getTipe());
        } else{
            final TipeModel tipeModel = listTipe.get(position);
            holder.tipe.setText(tipeModel.getTipe());
            holder.itemRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index = position;
                    ((RingkasanOrderActivity)mCtx).saleslistener(tipeModel.getIdsaltipe(),tipeModel.getTipe());
                    notifyDataSetChanged();
                }
            });

            if(index==position){
                holder.itemRow.setBackgroundColor(Color.parseColor("#BFEBFF"));
            }else{
                holder.itemRow.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return listTipe.size();
    }
}
