package com.ultra.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProdukModel implements Serializable {

    @SerializedName("")
    @Expose
    private String idProduk;

    @SerializedName("")
    @Expose
    private String namaProduk;

    @SerializedName("")
    @Expose
    private String hargaProduk;

    @SerializedName("")
    @Expose
    private String gambarProduk;

    public ProdukModel(String idProduk, String namaProduk, String hargaProduk, String gambarProduk){
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.gambarProduk = gambarProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public void setHargaProduk(String hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public void setGambarProduk(String gambarProduk) {
        this.gambarProduk = gambarProduk;
    }

    public String getIdProduk() {
        return idProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public String getHargaProduk() {
        return hargaProduk;
    }

    public String getGambarProduk() {
        return gambarProduk;
    }
}
