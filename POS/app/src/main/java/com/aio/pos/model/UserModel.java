package com.aio.pos.model;

import androidx.browser.browseractions.BrowserActionsIntent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable {

    @SerializedName("iduser")
    @Expose
    private String idUser;

    @SerializedName("nama_user")
    @Expose
    private String namaUser;

    @SerializedName("telp_user")
    @Expose
    private String telpUser;

    @SerializedName("email_user")
    @Expose
    private String emailUser;

    @SerializedName("role_user")
    @Expose
    private String roleUser;

    @SerializedName("idbusiness")
    @Expose
    private String idBusiness;

    @SerializedName("idoutlet")
    @Expose
    private String idOutlet;

    @SerializedName("status_user")
    @Expose
    private String statusUser;

    @SerializedName("idtb")
    @Expose
    private String idTb;

    @SerializedName("nama_business")
    @Expose
    private String namaBisnis;

    @SerializedName("alamat_business")
    @Expose
    private String alamatBisnis;

    @SerializedName("name_outlet")
    @Expose
    private String namaOutlet;

    @SerializedName("alamat_outlet")
    @Expose
    private String alamatOutlet;

    @SerializedName("idtax")
    @Expose
    private String idTax;

    @SerializedName("nama_tax")
    @Expose
    private String namaTax;

    @SerializedName("besaran_tax")
    @Expose
    private String besaranTax;

    public UserModel(String idUser, String namaUser, String telpUser, String emailUser, String roleUser, String idBusiness, String idOutlet,
                     String statusUser, String idTb,String namaBisnis, String alamatBisnis, String namaOutlet, String alamatOutlet, String idTax, String namaTax, String besaranTax){
        this.idUser = idUser;
        this.namaUser = namaUser;
        this.telpUser = telpUser;
        this.emailUser = emailUser;
        this.roleUser = roleUser;
        this.idBusiness = idBusiness;
        this.idOutlet = idOutlet;
        this.statusUser = statusUser;
        this.idTb = idTb;
        this.namaBisnis = namaBisnis;
        this.alamatBisnis = alamatBisnis;
        this.namaOutlet = namaOutlet;
        this.alamatOutlet = alamatOutlet;
        this.idTax = idTax;
        this.namaTax = namaTax;
        this.besaranTax = besaranTax;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public void setTelpUser(String telpUser) {
        this.telpUser = telpUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public void setRoleUser(String roleUser) {
        this.roleUser = roleUser;
    }

    public void setIdBusiness(String idBusiness) {
        this.idBusiness = idBusiness;
    }

    public void setIdOutlet(String idOutlet) {
        this.idOutlet = idOutlet;
    }

    public void setStatusUser(String statusUser) {
        this.statusUser = statusUser;
    }

    public void setIdTb(String idTb) {
        this.idTb = idTb;
    }

    public void setNamaBisnis(String namaBisnis) {
        this.namaBisnis = namaBisnis;
    }

    public void setAlamatBisnis(String alamatBisnis) {
        this.alamatBisnis = alamatBisnis;
    }

    public void setNamaOutlet(String namaOutlet) {
        this.namaOutlet = namaOutlet;
    }

    public void setAlamatOutlet(String alamatOutlet) {
        this.alamatOutlet = alamatOutlet;
    }

    public void setIdTax(String idTax) {
        this.idTax = idTax;
    }

    public void setNamaTax(String namaTax) {
        this.namaTax = namaTax;
    }

    public void setBesaranTax(String besaranTax) {
        this.besaranTax = besaranTax;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public String getTelpUser() {
        return telpUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public String getRoleUser() {
        return roleUser;
    }

    public String getIdBusiness() {
        return idBusiness;
    }

    public String getIdOutlet() {
        return idOutlet;
    }

    public String getStatusUser() {
        return statusUser;
    }

    public String getIdTb() {
        return idTb;
    }

    public String getNamaBisnis() {
        return namaBisnis;
    }

    public String getAlamatBisnis() {
        return alamatBisnis;
    }

    public String getNamaOutlet() {
        return namaOutlet;
    }

    public String getAlamatOutlet() {
        return alamatOutlet;
    }

    public String getIdTax() {
        return idTax;
    }

    public String getNamaTax() {
        return namaTax;
    }

    public String getBesaranTax() {
        return besaranTax;
    }
}
