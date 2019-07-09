package com.ultra.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TipeModel implements Serializable {
    @SerializedName("")
    @Expose
    private String tipe;

    @SerializedName("")
    @Expose
    private String idsaltipe;

    public TipeModel(String idsaltipe,String tipe){
        this.idsaltipe=idsaltipe;
        this.tipe = tipe;
    }

    public String getTipe() {
        return tipe;
    }

    public String getIdsaltipe() {
        return idsaltipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public void setIdsaltipe(String idsaltipe) {
        this.idsaltipe = idsaltipe;
    }
}
