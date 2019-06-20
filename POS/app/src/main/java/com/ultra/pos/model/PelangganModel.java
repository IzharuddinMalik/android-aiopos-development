package com.ultra.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PelangganModel implements Serializable {

    @SerializedName("")
    @Expose
    private String idPelanggan;

    @SerializedName("")
    @Expose
    private String idBusiness;

    @SerializedName("")
    @Expose
    private String idOutlet;

    @SerializedName("")
    @Expose
    private String idProvince;

    @SerializedName("")
    @Expose
    private String idRegencies;

    @SerializedName("")
    @Expose
    private String idDistrict;

    @SerializedName("")
    @Expose
    private String namaPelanggan;

    @SerializedName("")
    @Expose
    private String emailPelanggan;

    @SerializedName("")
    @Expose
    private String telpPelanggan;

    @SerializedName("")
    @Expose
    private String telpPelanggan2;

    public PelangganModel(String idPelanggan, String idBusiness, String idOutlet, String idProvince, String idRegencies, String idDistrict,
                          String namaPelanggan, String emailPelanggan, String telpPelanggan, String telpPelanggan2){
        this.idPelanggan = idPelanggan;
        this.idBusiness = idBusiness;
        this.idOutlet = idOutlet;
        this.idProvince = idProvince;
        this.idRegencies = idRegencies;
        this.idDistrict = idDistrict;
        this.namaPelanggan = namaPelanggan;
        this.emailPelanggan = emailPelanggan;
        this.telpPelanggan = telpPelanggan;
        this.telpPelanggan2 = telpPelanggan2;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public void setIdBusiness(String idBusiness) {
        this.idBusiness = idBusiness;
    }

    public void setIdOutlet(String idOutlet) {
        this.idOutlet = idOutlet;
    }

    public void setIdProvince(String idProvince) {
        this.idProvince = idProvince;
    }

    public void setIdRegencies(String idRegencies) {
        this.idRegencies = idRegencies;
    }

    public void setIdDistrict(String idDistrict) {
        this.idDistrict = idDistrict;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public void setEmailPelanggan(String emailPelanggan) {
        this.emailPelanggan = emailPelanggan;
    }

    public void setTelpPelanggan(String telpPelanggan) {
        this.telpPelanggan = telpPelanggan;
    }

    public void setTelpPelanggan2(String telpPelanggan2) {
        this.telpPelanggan2 = telpPelanggan2;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getIdBusiness() {
        return idBusiness;
    }

    public String getIdOutlet() {
        return idOutlet;
    }

    public String getIdProvince() {
        return idProvince;
    }

    public String getIdRegencies() {
        return idRegencies;
    }

    public String getIdDistrict() {
        return idDistrict;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public String getEmailPelanggan() {
        return emailPelanggan;
    }

    public String getTelpPelanggan() {
        return telpPelanggan;
    }

    public String getTelpPelanggan2() {
        return telpPelanggan2;
    }
}
