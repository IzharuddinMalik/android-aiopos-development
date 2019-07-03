package com.ultra.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Produk implements Serializable {

    @SerializedName("idproduk")
    @Expose
    private String idProduk;

    @SerializedName("nama_produk")
    @Expose
    private String namaProduk;

    @SerializedName("foto_produk")
    @Expose
    private String fotoProduk;

    @SerializedName("harga_produk")
    @Expose
    private String hargaProduk;

    @SerializedName("idkategori")
    @Expose
    private String idKategori;

    public Produk(String idProduk, String namaProduk, String fotoProduk, String hargaProduk, String idKategori){
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.fotoProduk = fotoProduk;
        this.hargaProduk = hargaProduk;
        this.idKategori = idKategori;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public void setFotoProduk(String fotoProduk) {
        this.fotoProduk = fotoProduk;
    }

    public void setHargaProduk(String hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public String getIdProduk() {
        return idProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public String getFotoProduk() {
        return fotoProduk;
    }

    public String getHargaProduk() {
        return hargaProduk;
    }

    public String getIdKategori() {
        return idKategori;
    }
}
