package com.ultra.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PesananModel implements Serializable {

    private String idPesanan;

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

    @SerializedName("harga_variant")
    @Expose
    private String hargaVariant;

    @SerializedName("harga_produk")
    @Expose
    private String hargaProduk;

    @SerializedName("foto_produk")
    @Expose
    private String fotoProduk;

    @SerializedName("idkategori")
    @Expose
    private String idKategori;


    public PesananModel(String idPesanan, String idProduk, String namaProduk, String idVariant, String namaVariant, String hargaVariant, String hargaProduk, String fotoProduk, String idKategori){
        this.idPesanan = idPesanan;
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.idVariant = idVariant;
        this.namaVariant = namaVariant;
        this.hargaVariant = hargaVariant;
        this.fotoProduk = fotoProduk;
        this.hargaProduk = hargaProduk;
        this.idKategori = idKategori;
    }

    public PesananModel(String idPesanan, String idProduk, String idKategori, String idVariant, String namaVariant, String namaPesanan, String hargaPesanan) {
        this.idPesanan = idPesanan;
        this.idProduk = idProduk;
        this.idKategori = idKategori;
        this.idVariant = idVariant;
        this.namaVariant = namaVariant;
        this.namaProduk = namaPesanan;
        this.hargaProduk = hargaPesanan;
    }

    public void setIdPesanan(String idPesanan) {
        this.idPesanan = idPesanan;
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

    public void setIdVariant(String idVariant) {
        this.idVariant = idVariant;
    }

    public void setNamaVariant(String namaVariant) {
        this.namaVariant = namaVariant;
    }

    public void setHargaVariant(String hargaVariant) {
        this.hargaVariant = hargaVariant;
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

    public String getHargaVariant() {
        return hargaVariant;
    }

    public String getIdVariant() {
        return idVariant;
    }

    public String getNamaVariant() {
        return namaVariant;
    }

    public String getIdPesanan() {
        return idPesanan;
    }
}
