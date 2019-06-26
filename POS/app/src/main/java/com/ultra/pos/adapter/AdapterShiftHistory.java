package com.ultra.pos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ultra.pos.R;
import com.ultra.pos.model.HistoryModel;
import com.ultra.pos.util.StylingUtils;

import java.util.List;

public class AdapterShiftHistory extends RecyclerView.Adapter<AdapterShiftHistory.HistoryViewHolder> {
    private Context mCtx;
    private List<HistoryModel> listHistory;
    StylingUtils stylingUtils;

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_list_history, parent, false);

        return new AdapterShiftHistory.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryViewHolder holder, final int position) {
        final HistoryModel historyModel = listHistory.get(position);
        holder.tanggal.setText(historyModel.getTanggal());
    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView tanggal;
        public HistoryViewHolder(View view){
            super(view);

            tanggal=view.findViewById(R.id.tvTanggalShiftHistory);

            stylingUtils.robotoRegularTextview(mCtx, tanggal);
        }
    }

    public AdapterShiftHistory(Context context, List<HistoryModel> listHistory){
        this.mCtx = context;
        this.listHistory = listHistory;

        stylingUtils = new StylingUtils();
    }
}
