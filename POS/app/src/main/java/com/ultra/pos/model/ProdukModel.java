package com.ultra.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProdukModel implements Serializable {

    @SerializedName("idproduk")
    @Expose
    private String idProduk;

    @SerializedName("idbusiness")
    @Expose
    private String idBusiness;

    @SerializedName("idoutlet")
    @Expose
    private String idOutlet;

    @SerializedName("nama_outlet")
    @Expose
    private String namaOutlet;

    @SerializedName("nama_produk")
    @Expose
    private String namaProduk;

    @SerializedName("variant")
    @Expose
    private String variant;

    @SerializedName("idkategori")
    @Expose
    private String idKategori;

    @SerializedName("nama_variant")
    @Expose
    private String namaVariant;

    @SerializedName("status_produk")
    @Expose
    private String statusProduk;

    @SerializedName("foto_produk")
    @Expose
    private String gambarProduk;

    @SerializedName("harga")
    @Expose
    private String hargaProduk;

    public ProdukModel(String idProduk, String idBusiness, String idOutlet, String namaOutlet, String namaProduk, String variant, String idKategori,
                       String namaVariant, String statusProduk , String gambarProduk, String hargaProduk){
        this.idProduk = idProduk;
        this.idBusiness = idBusiness;
        this.idOutlet = idOutlet;
        this.namaOutlet = namaOutlet;
        this.namaProduk = namaProduk;
        this.variant = variant;
        this.idKategori = idKategori;
        this.namaVariant = namaVariant;
        this.statusProduk = statusProduk;
        this.gambarProduk = gambarProduk;
        this.hargaProduk = hargaProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public void setIdBusiness(String idBusiness) {
        this.idBusiness = idBusiness;
    }

    public void setIdOutlet(String idOutlet) {
        this.idOutlet = idOutlet;
    }

    public void setNamaOutlet(String namaOutlet) {
        this.namaOutlet = namaOutlet;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public void setNamaVariant(String namaVariant) {
        this.namaVariant = namaVariant;
    }

    public void setStatusProduk(String statusProduk) {
        this.statusProduk = statusProduk;
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

    public String getIdBusiness() {
        return idBusiness;
    }

    public String getIdOutlet() {
        return idOutlet;
    }

    public String getNamaOutlet() {
        return namaOutlet;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public String getVariant() {
        return variant;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public String getNamaVariant() {
        return namaVariant;
    }

    public String getStatusProduk() {
        return statusProduk;
    }

    public String getHargaProduk() {
        return hargaProduk;
    }

    public String getGambarProduk() {
        return gambarProduk;
    }
}
