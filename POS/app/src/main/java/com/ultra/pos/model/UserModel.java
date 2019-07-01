package com.ultra.pos.model;

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

    public UserModel(String idUser, String namaUser, String telpUser, String emailUser, String roleUser, String idBusiness, String idOutlet,
                     String statusUser, String idTb){
        this.idUser = idUser;
        this.namaUser = namaUser;
        this.telpUser = telpUser;
        this.emailUser = emailUser;
        this.roleUser = roleUser;
        this.idBusiness = idBusiness;
        this.idOutlet = idOutlet;
        this.statusUser = statusUser;
        this.idTb = idTb;
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
}
