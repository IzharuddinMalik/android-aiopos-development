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
    private String namaProvinsi;

    @SerializedName("")
    @Expose
    private String namaKabKota;

    @SerializedName("")
    @Expose
    private String namaKecamatan;

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

    public PelangganModel(String idPelanggan, String idBusiness, String idOutlet, String namaProvinsi, String namaKabKota, String namaKecamatan,
                               String namaPelanggan, String emailPelanggan, String telpPelanggan, String telpPelanggan2){
        this.idPelanggan = idPelanggan;
        this.idBusiness = idBusiness;
        this.idOutlet = idOutlet;
        this.namaProvinsi = namaProvinsi;
        this.namaKabKota = namaKabKota;
        this.namaKecamatan = namaKecamatan;
        this.namaPelanggan = namaPelanggan;
        this.emailPelanggan = emailPelanggan;
        this.telpPelanggan = telpPelanggan;
        this.telpPelanggan2 = telpPelanggan2;
    }

    public PelangganModel(String idPelanggan, String namaPelanggan, String telpPelanggan, String emailPelanggan){
        this.idPelanggan = idPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.emailPelanggan = emailPelanggan;
        this.telpPelanggan = telpPelanggan;
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

    public void setNamaProvinsi(String namaProvinsi) {
        this.namaProvinsi = namaProvinsi;
    }

    public void setNamaKabKota(String namaKabKota) {
        this.namaKabKota = namaKabKota;
    }

    public void setNamaKecamatan(String namaKecamatan) {
        this.namaKecamatan = namaKecamatan;
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

    public String getNamaKabKota() {
        return namaKabKota;
    }

    public String getNamaKecamatan() {
        return namaKecamatan;
    }

    public String getNamaProvinsi() {
        return namaProvinsi;
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
