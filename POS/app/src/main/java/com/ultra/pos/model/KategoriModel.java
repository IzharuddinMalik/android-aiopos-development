package com.ultra.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KategoriModel implements Serializable {

    @SerializedName("idkategori")
    @Expose
    private String idKategori;

    @SerializedName("idbusiness")
    @Expose
    private String idBusiness;

    @SerializedName("nama_kategori")
    @Expose
    private String namaKategori;

    public KategoriModel(String idKategori, String idBusiness, String namaKategori){
        this.idKategori = idKategori;
        this.idBusiness = idBusiness;
        this.namaKategori = namaKategori;
    }

    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public void setIdBusiness(String idBusiness) {
        this.idBusiness = idBusiness;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public String getIdBusiness() {
        return idBusiness;
    }

    public String getNamaKategori() {
        return namaKategori;
    }
}
