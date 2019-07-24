package com.aio.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HistoryModel implements Serializable {
    @SerializedName("")
    @Expose
    private String tanggal;

    @SerializedName("")
    @Expose
    private String data1;

    @SerializedName("")
    @Expose
    private String data2;

    public HistoryModel(String tanggal, String data1, String data2){
        this.tanggal = tanggal;
        this.data1 = data1;
        this.data2 = data2;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }
}
