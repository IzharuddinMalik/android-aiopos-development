package com.aiopos.apps.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.aiopos.apps.R;
import com.aiopos.apps.activity.DetailPelangganActivity;
import com.aiopos.apps.model.PelangganModel;
import com.aiopos.apps.util.StylingUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPilihPelanggan extends RecyclerView.Adapter<AdapterPilihPelanggan.PelangganViewHolder> implements Filterable {

    private Context mCtx;
    private List<PelangganModel> listPelanggan;
    private List<PelangganModel> mFilterlistPelanggan;
    StylingUtils stylingUtils;

    public class PelangganViewHolder extends RecyclerView.ViewHolder{
        CircleImageView listGambarPelanggan;
        TextView listNamaPelanggan, listNoTelpPelanggan, listEmailPelanggan;

        public PelangganViewHolder(View view){
            super(view);

            listGambarPelanggan = view.findViewById(R.id.civMenuPelangganListLogoPelanggan);
            listNamaPelanggan = view.findViewById(R.id.tvMenuPelangganListNamaPelanggan);
            listNoTelpPelanggan = view.findViewById(R.id.tvMenuPelangganListNoTelpPelanggan);
            listEmailPelanggan = view.findViewById(R.id.tvMenuPelangganListEmailPelanggan);

            view.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    Intent intent = new Intent(view.getContext(), DetailPelangganActivity.class);
                    intent.putExtra("idctm", listPelanggan.get(position).getIdPelanggan());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                }
            });

            stylingUtils.robotoRegularTextview(mCtx, listNamaPelanggan);
            stylingUtils.robotoRegularTextview(mCtx, listNoTelpPelanggan);
            stylingUtils.robotoRegularTextview(mCtx, listEmailPelanggan);
        }
    }

    public AdapterPilihPelanggan(Context context, List<PelangganModel> listPelanggan){
        this.mCtx = context;
        this.listPelanggan = listPelanggan;
        this.mFilterlistPelanggan = listPelanggan;

        stylingUtils = new StylingUtils();
    }

    public PelangganViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_list_pelanggan, parent, false);

        return new PelangganViewHolder(view);
    }

    public void onBindViewHolder(final PelangganViewHolder holder, int position){
        final PelangganModel pelangganModel = listPelanggan.get(position);
        holder.listNamaPelanggan.setText(pelangganModel.getNamaPelanggan());
        holder.listEmailPelanggan.setText(pelangganModel.getEmailPelanggan());
        holder.listNoTelpPelanggan.setText(pelangganModel.getTelpPelanggan());
    }

    public int getItemCount(){ return mFilterlistPelanggan.size();}

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilterlistPelanggan = listPelanggan;
                } else {

                    List<PelangganModel> filteredList = new ArrayList<PelangganModel>();

                    int i = 0;
                    for (PelangganModel name : listPelanggan) {

                        if (name.getNamaPelanggan().toLowerCase().contains(charString) || name.getNamaPelanggan().toUpperCase().contains(charString)) {
                            filteredList.add(i, new PelangganModel(name.getNamaPelanggan(), name.getEmailPelanggan(), name.getTelpPelanggan()));
                            i = i+1;
                        }
                    }

                    mFilterlistPelanggan = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterlistPelanggan;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilterlistPelanggan = (ArrayList<PelangganModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
