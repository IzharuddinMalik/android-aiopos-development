package com.aio.pos.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.aio.pos.R;
import com.aio.pos.activity.PembayaranActivity;
import com.aio.pos.model.EDCModel;
import com.aio.pos.util.StylingUtils;

import java.util.List;

public class AdapterEDC extends RecyclerView.Adapter<AdapterEDC.EDCViewHolder> {

    private Context mCtx;
    private List<EDCModel> listEDC;
    StylingUtils stylingUtils;
    int index=-1;

    @Override
    public EDCViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_list_edc, parent, false);

        return new AdapterEDC.EDCViewHolder(view);
    }

    public AdapterEDC(Context context, List<EDCModel> listEDC){
        this.mCtx = context;
        this.listEDC = listEDC;

        stylingUtils = new StylingUtils();
    }

    @Override
    public void onBindViewHolder(EDCViewHolder holder, final int position) {
        final EDCModel edcModel = listEDC.get(position);
        holder.nama.setText(edcModel.getNama_Pay());
        holder.EDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                ((PembayaranActivity)mCtx).settype_pembayaran(edcModel.getNama_Pay());
                notifyDataSetChanged();
            }
        });

        if(index==position){
            holder.EDC.setBackgroundColor(Color.parseColor("#BFEBFF"));
        }else{
            holder.EDC.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return listEDC.size();
    }

    public class EDCViewHolder extends RecyclerView.ViewHolder {
        TextView nama;
        LinearLayout EDC;
        public EDCViewHolder(View view) {
            super(view);

            nama=view.findViewById(R.id.tvNamaEDC);
            EDC=view.findViewById(R.id.llEDC);
            stylingUtils.robotoRegularTextview(mCtx,  nama);
        }
    }
}
