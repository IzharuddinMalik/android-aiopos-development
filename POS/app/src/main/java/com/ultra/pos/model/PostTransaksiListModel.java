package com.ultra.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostTransaksiListModel implements Serializable {

    @SerializedName("")
    @Expose
    private String idproduk;

    @SerializedName("")
    @Expose
    private String idvariant;

    @SerializedName("")
    @Expose
    private String idpaket;

    @SerializedName("")
    @Expose
    private String qty;

    @SerializedName("")
    @Expose
    private String hargasatuan;

    @SerializedName("")
    @Expose
    private String idtax;

    public PostTransaksiListModel(String idproduk,String idvariant, String idpaket,String qty,String hargasatuan,String idtax){
        this.idproduk=idproduk;
        this.idvariant=idvariant;
        this.idpaket=idpaket;
        this.qty=qty;
        this.hargasatuan=hargasatuan;
        this.idtax=idtax;
    }

    public void setHargasatuan(String hargasatuan) {
        this.hargasatuan = hargasatuan;
    }

    public void setIdproduk(String idproduk) {
        this.idproduk = idproduk;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setIdpaket(String idpaket) {
        this.idpaket = idpaket;
    }

    public void setIdtax(String idtax) {
        this.idtax = idtax;
    }

    public void setIdvariant(String idvariant) {
        this.idvariant = idvariant;
    }

    public String getHargasatuan() {
        return hargasatuan;
    }

    public String getIdproduk() {
        return idproduk;
    }

    public String getQty() {
        return qty;
    }

    public String getIdpaket() {
        return idpaket;
    }

    public String getIdvariant() {
        return idvariant;
    }

    public String getIdtax() {
        return idtax;
    }
}
