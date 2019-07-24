package com.aio.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KecamatanModel implements Serializable {

    @SerializedName("id")
    @Expose
    private String idKecamatan;

    @SerializedName("name")
    @Expose
    private String namaKecamatan;

    public KecamatanModel(String idKecamatan, String namaKecamatan){
        this.idKecamatan = idKecamatan;
        this.namaKecamatan = namaKecamatan;
    }

    public void setIdKecamatan(String idKecamatan) {
        this.idKecamatan = idKecamatan;
    }

    public void setNamaKecamatan(String namaKecamatan) {
        this.namaKecamatan = namaKecamatan;
    }

    public String getIdKecamatan() {
        return idKecamatan;
    }

    public String getNamaKecamatan() {
        return namaKecamatan;
    }
}
