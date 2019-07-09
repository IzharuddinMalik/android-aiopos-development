package com.ultra.pos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ultra.pos.R;
import com.ultra.pos.model.EDCModel;
import com.ultra.pos.util.StylingUtils;

import java.util.List;

public class AdapterEDC extends RecyclerView.Adapter<AdapterEDC.EDCViewHolder> {

    private Context mCtx;
    private List<EDCModel> listEDC;
    StylingUtils stylingUtils;

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
    }

    @Override
    public int getItemCount() {
        return listEDC.size();
    }

    public class EDCViewHolder extends RecyclerView.ViewHolder {
        TextView nama;
        public EDCViewHolder(View view) {
            super(view);

            nama=view.findViewById(R.id.tvNamaEDC);
            stylingUtils.robotoRegularTextview(mCtx,  nama);
        }
    }
}
