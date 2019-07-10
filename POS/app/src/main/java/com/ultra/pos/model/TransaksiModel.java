package com.ultra.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransaksiModel implements Serializable {
    @SerializedName("")
    @Expose
    private String tipepembayaran;

    @SerializedName("")
    @Expose
    private String idtrans;

    @SerializedName("")
    @Expose
    private String total;

    @SerializedName("")
    @Expose
    private String nomor;

    @SerializedName("")
    @Expose
    private String status;

    @SerializedName("")
    @Expose
    private String jam;


    public TransaksiModel(String idtrans,String total, String nomor, String status, String jam,String tipepembayaran){
        this.total = total;
        this.nomor = nomor;
        this.status = status;
        this.jam = jam;
        this.tipepembayaran=tipepembayaran;
        this.idtrans=idtrans;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getTipepembayaran() {
        return tipepembayaran;
    }

    public void setTipepembayaran(String tipepembayaran) {
        this.tipepembayaran = tipepembayaran;
    }

    public String getIdtrans() {
        return idtrans;
    }

    public void setIdtrans(String idtrans) {
        this.idtrans = idtrans;
    }
}
