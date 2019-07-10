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

    @SerializedName("idvariant")
    @Expose
    private String idVariant;

    @SerializedName("nama_variant")
    @Expose
    private String namaVariant;

    @SerializedName("harga_produk")
    @Expose
    private String hargaProduk;

    @SerializedName("foto_produk")
    @Expose
    private String fotoProduk;

    @SerializedName("jumlah_produk")
    @Expose
    private String jumlahProduk;

    @SerializedName("idkategori")
    @Expose
    private String idKategori;

    public Produk(String idProduk, String namaProduk, String idVariant, String namaVariant, String hargaProduk, String fotoProduk, String jumlahProduk, String idKategori){
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.idVariant = idVariant;
        this.namaVariant = namaVariant;
        this.fotoProduk = fotoProduk;
        this.jumlahProduk = jumlahProduk;
        this.hargaProduk = hargaProduk;
        this.idKategori = idKategori;
    }

    public Produk(String idProduk, String namaProduk, String idVariant, String namaVariant, String hargaProduk, String fotoProduk, String idKategori){
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.idVariant = idVariant;
        this.namaVariant = namaVariant;
        this.hargaProduk = hargaProduk;
        this.fotoProduk = fotoProduk;
        this.idKategori = idKategori;
    }

    public Produk(String namaProduk, String namaVariant, String hargaProduk, String jumlahProduk){
        this.namaProduk = namaProduk;
        this.namaVariant = namaVariant;
        this.hargaProduk = hargaProduk;
        this.jumlahProduk = jumlahProduk;
    }

    public Produk(String idProduk, String namaProduk, String hargaProduk, String namaVariant, String idVariant) {

    }

    public Produk(String idProduk, String namaProduk, String hargaProduk) {

    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public void setJumlahProduk(String jumlahProduk) {
        this.jumlahProduk = jumlahProduk;
    }

    public void setHargaProduk(String hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public void setIdVariant(String idVariant) {
        this.idVariant = idVariant;
    }

    public void setNamaVariant(String namaVariant) {
        this.namaVariant = namaVariant;
    }

    public void setFotoProduk(String fotoProduk) {
        this.fotoProduk = fotoProduk;
    }


    public String getIdProduk() {
        return idProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public String getJumlahProduk() {
        return jumlahProduk;
    }

    public String getHargaProduk() {
        return hargaProduk;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public String getIdVariant() {
        return idVariant;
    }

    public String getNamaVariant() {
        return namaVariant;
    }

    public String getFotoProduk() {
        return fotoProduk;
    }

}
