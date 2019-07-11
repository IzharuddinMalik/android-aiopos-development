package com.aio.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EDCModel implements Serializable {
    @SerializedName("")
    @Expose
    private String idPay;

    @SerializedName("")
    @Expose
    private String nama_Pay;

    public EDCModel(String idPay,String nama_Pay){
        this.idPay=idPay;
        this.nama_Pay=nama_Pay;

    }

    public void setIdPay(String idPay) {
        this.idPay = idPay;
    }

    public void setNama_Pay(String nama_Pay) {
        this.nama_Pay = nama_Pay;
    }

    public String getIdPay() {
        return idPay;
    }

    public String getNama_Pay() {
        return nama_Pay;
    }
}
