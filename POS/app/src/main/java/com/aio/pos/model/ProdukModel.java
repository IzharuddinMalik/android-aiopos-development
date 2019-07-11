package com.aio.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProdukModel implements Serializable {

    @SerializedName("idkategori")
    @Expose
    private String idKategori;

    @SerializedName("nama_kategori")
    @Expose
    private String namaKategori;

    @SerializedName("produk")
    @Expose
    private List<Produk> dataProduk = new ArrayList<Produk>();

    public ProdukModel(String idKategori, String namaKategori, List<Produk> dataProduk){
        this.idKategori = idKategori;
        this.namaKategori = namaKategori;
        this.dataProduk = dataProduk;
    }

    public ProdukModel(String idkategori, String nama_kategori, String produk) {

    }

    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public void setDataProduk(List<Produk> dataProduk) {
        this.dataProduk = dataProduk;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public List<Produk> getDataProduk() {
        return dataProduk;
    }
}
