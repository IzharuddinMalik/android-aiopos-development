package com.ultra.pos.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ultra.pos.R;
import com.ultra.pos.model.PelangganModel;
import com.ultra.pos.util.StylingUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPilihPelanggan extends RecyclerView.Adapter<AdapterPilihPelanggan.PelangganViewHolder> {

    private Context mCtx;
    private List<PelangganModel> listPelanggan;
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

            stylingUtils.robotoRegularTextview(mCtx, listNamaPelanggan);
            stylingUtils.robotoRegularTextview(mCtx, listNoTelpPelanggan);
            stylingUtils.robotoRegularTextview(mCtx, listEmailPelanggan);
        }
    }

    public AdapterPilihPelanggan(Context context, List<PelangganModel> listPelanggan){
        this.mCtx = context;
        this.listPelanggan = listPelanggan;

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

    public int getItemCount(){ return listPelanggan.size();}
}
