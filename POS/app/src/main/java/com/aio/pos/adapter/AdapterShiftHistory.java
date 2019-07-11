package com.aio.pos.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aio.pos.R;

import com.aio.pos.activity.DetailShiftActivity;
import com.aio.pos.model.HistoryModel;
import com.aio.pos.util.StylingUtils;

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

            view.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    Intent intent = new Intent(view.getContext(), DetailShiftActivity.class);
                    intent.putExtra("data1", listHistory.get(position).getData1());
                    intent.putExtra("data2", listHistory.get(position).getData2());
                    intent.putExtra("tanggal", listHistory.get(position).getTanggal());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                }
            });

            stylingUtils.robotoRegularTextview(mCtx, tanggal);
        }
    }

    public AdapterShiftHistory(Context context, List<HistoryModel> listHistory){
        this.mCtx = context;
        this.listHistory = listHistory;

        stylingUtils = new StylingUtils();
    }
}
