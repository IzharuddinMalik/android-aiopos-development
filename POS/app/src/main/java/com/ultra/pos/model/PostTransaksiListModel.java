package com.ultra.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostTransaksiListModel implements Serializable {

    @SerializedName("")
    @Expose
    private String idvarpro;

    @SerializedName("")
    @Expose
    private String qty;

    @SerializedName("")
    @Expose
    private String hargasatuan;

    public PostTransaksiListModel(String idvarpro,String qty,String hargasatuan){
        this.idvarpro=idvarpro;
        this.qty=qty;
        this.hargasatuan=hargasatuan;
    }

    public void setHargasatuan(String hargasatuan) {
        this.hargasatuan = hargasatuan;
    }

    public void setIdvarpro(String idvarpro) {
        this.idvarpro = idvarpro;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getHargasatuan() {
        return hargasatuan;
    }

    public String getIdvarpro() {
        return idvarpro;
    }

    public String getQty() {
        return qty;
    }
}
