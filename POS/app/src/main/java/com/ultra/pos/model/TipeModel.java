package com.ultra.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TipeModel implements Serializable {
    @SerializedName("")
    @Expose
    private String idTipe;

    @SerializedName("")
    @Expose
    private String tipe;

    public TipeModel(String idTipe,String tipe){
        this.idTipe = idTipe;
        this.tipe = tipe;
    }

    public TipeModel() {

    }

    public String getTipe() {
        return tipe;
    }

    public String getIdTipe() {
        return idTipe;
    }

    public void setIdTipe(String idTipe) {
        this.idTipe = idTipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
}
