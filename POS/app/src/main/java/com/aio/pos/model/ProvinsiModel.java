package com.aio.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProvinsiModel implements Serializable {

    @SerializedName("id")
    @Expose
    private String idWilayah;

    @SerializedName("name")
    @Expose
    private String namaWilayah;

    public ProvinsiModel(String idWilayah, String namaWilayah){
        this.idWilayah = idWilayah;
        this.namaWilayah = namaWilayah;
    }

    public void setIdWilayah(String idWilayah) {
        this.idWilayah = idWilayah;
    }

    public void setNamaWilayah(String namaWilayah) {
        this.namaWilayah = namaWilayah;
    }

    public String getIdWilayah() {
        return idWilayah;
    }

    public String getNamaWilayah() {
        return namaWilayah;
    }
}
