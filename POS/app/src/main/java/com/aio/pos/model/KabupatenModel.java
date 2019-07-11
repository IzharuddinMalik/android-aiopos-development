package com.aio.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KabupatenModel implements Serializable {

    @SerializedName("id")
    @Expose
    private String idKabupaten;

    @SerializedName("name")
    private String namaKabupaten;

    public KabupatenModel(String idKabupaten, String namaKabupaten){
        this.idKabupaten = idKabupaten;
        this.namaKabupaten = namaKabupaten;
    }

    public void setIdKabupaten(String idKabupaten) {
        this.idKabupaten = idKabupaten;
    }

    public void setNamaKabupaten(String namaKabupaten) {
        this.namaKabupaten = namaKabupaten;
    }

    public String getIdKabupaten() {
        return idKabupaten;
    }

    public String getNamaKabupaten() {
        return namaKabupaten;
    }
}
