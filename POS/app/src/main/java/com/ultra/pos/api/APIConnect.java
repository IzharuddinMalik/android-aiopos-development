package com.ultra.pos.api;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import com.ultra.pos.R;
import com.ultra.pos.util.ResponseCodeToText;
import com.ultra.pos.util.StylingUtils;
import com.ultra.pos.util.ToastUtils;

import pl.droidsonroids.gif.GifImageView;

public class APIConnect {

    BaseApiInterface mApiInterface;
    Context context;
    View viewtoastsucces, viewtoastinfo, viewtoastfailed;
    Dialog dialogloading;
    ResponseCodeToText responseCodeToText;
    StylingUtils stylingUtils;
    ToastUtils toastUtils;
    Boolean isdialogshow1 = false;

    public void showdialogloading(){
        dialogloading = new Dialog(context);
        // Include dialog.xml file
        dialogloading.setContentView(R.layout.dialog_loading);
        dialogloading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogloading.setCancelable(false);

        dialogloading.setOnDismissListener(dialog -> isdialogshow1 = false);

        if(!isdialogshow1) {
            isdialogshow1 = true;
            dialogloading.show();

            final GifImageView imgloading = dialogloading.findViewById(R.id.imgloading);
            TextView tvloading = dialogloading.findViewById(R.id.tvloading);
            stylingUtils.robotoRegularTextview(context, tvloading);

            imgloading.setImageResource(R.drawable.iconloading);

        }
    }

    public void showtoastsucces(String text){
        final TextView tvtoastsuccess = viewtoastsucces.findViewById(R.id.tvtoast);

        stylingUtils.robotoRegularTextview(context, tvtoastsuccess);
        tvtoastsuccess.setText(text);
        toastUtils.toastbottomshort(viewtoastsucces);
    }

    public void showtoastinfo(String text){
        final TextView tvtoastinfo = viewtoastinfo.findViewById(R.id.tvtoast);

        stylingUtils.robotoRegularTextview(context, tvtoastinfo);
        tvtoastinfo.setText(text);
        toastUtils.toastbottomshort(viewtoastinfo);
    }

    public void showtoastfailed(String text){
        final TextView tvtoastfailed = viewtoastfailed.findViewById(R.id.tvtoast);

        stylingUtils.robotoRegularTextview(context, tvtoastfailed);
        tvtoastfailed.setText(text);
        toastUtils.toastbottomshort(viewtoastfailed);
    }
}
