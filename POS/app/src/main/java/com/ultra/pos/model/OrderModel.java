package com.ultra.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderModel implements Serializable {
    @SerializedName("")
    @Expose
    private String nama;

    @SerializedName("")
    @Expose
    private String jumlah;

    @SerializedName("")
    @Expose
    private String harga;

    public OrderModel(String nama, String jumlah, String harga){
        this.nama = nama;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
