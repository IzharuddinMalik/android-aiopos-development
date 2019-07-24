package com.aio.pos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PostTransaksiModel implements Serializable {

    @SerializedName("idtb")
    @Expose
    private String idTb;

    @SerializedName("idbusiness")
    @Expose
    private String idBusiness;

    @SerializedName("idcop")
    @Expose
    private String idCop;

    @SerializedName("idoutlet")
    @Expose
    private String idOutlet;

    @SerializedName("idctm")
    @Expose
    private String idCtm;

    @SerializedName("noinv_transHD")
    @Expose
    private String noInvTransHD;

    @SerializedName("diskon")
    @Expose
    private String diskonTransaksi;

    @SerializedName("status_transHD")
    @Expose
    private String statusTransHD;

    @SerializedName("produklist")
    @Expose
    private ArrayList<PostTransaksiListModel> postTransaksiListModels;

    @SerializedName("iduser")
    @Expose
    private String idUser;

    @SerializedName("idpay")
    @Expose
    private String idPay;

    @SerializedName("idsaltype")
    @Expose
    private String idSaltype;

    @SerializedName("idrefund")
    @Expose
    private String idRefund;

    @SerializedName("type_trans")
    @Expose
    private String typeTrans;

    @SerializedName("ket_refund")
    @Expose
    private String ketRefund;

    @SerializedName("total_transHD")
    @Expose
    private String totalTransHD;

    @SerializedName("idtax")
    @Expose
    private String idTax;

    @SerializedName("pay_transHD")
    @Expose
    private String payTransHD;

    @SerializedName("cashback_transHD")
    @Expose
    private String cashBackTransHD;

    public PostTransaksiModel(String idTb, String idBusiness, String idCop, String idOutlet, String idCtm, String noInvTransHD, String diskonTransaksi, String statusTransHD, ArrayList<PostTransaksiListModel> postTransaksiListModels,
                              String idUser, String idPay, String idSaltype, String idRefund, String typeTrans, String ketRefund, String totalTransHD, String idTax, String payTransHD, String cashBackTransHD){
        this.idTb = idTb;
        this.idBusiness = idBusiness;
        this.idCop = idCop;
        this.idOutlet = idOutlet;
        this.idCtm = idCtm;
        this.noInvTransHD = noInvTransHD;
        this.diskonTransaksi = diskonTransaksi;
        this.statusTransHD = statusTransHD;
        this.postTransaksiListModels = postTransaksiListModels;
        this.idUser = idUser;
        this.idPay = idPay;
        this.idSaltype = idSaltype;
        this.idRefund = idRefund;
        this.typeTrans = typeTrans;
        this.ketRefund = ketRefund;
        this.totalTransHD = totalTransHD;
        this.idTax = idTax;
        this.payTransHD = payTransHD;
        this.cashBackTransHD = cashBackTransHD;
    }

    public PostTransaksiModel() {

    }

    public void setIdTb(String idTb) {
        this.idTb = idTb;
    }

    public void setIdBusiness(String idBusiness) {
        this.idBusiness = idBusiness;
    }

    public void setIdCop(String idCop) {
        this.idCop = idCop;
    }

    public void setIdOutlet(String idOutlet) {
        this.idOutlet = idOutlet;
    }

    public void setIdCtm(String idCtm) {
        this.idCtm = idCtm;
    }

    public void setNoInvTransHD(String noInvTransHD) {
        this.noInvTransHD = noInvTransHD;
    }

    public void setDiskonTransaksi(String diskonTransaksi) {
        this.diskonTransaksi = diskonTransaksi;
    }

    public void setStatusTransHD(String statusTransHD) {
        this.statusTransHD = statusTransHD;
    }

    public void setPostTransaksiListModels(ArrayList<PostTransaksiListModel> postTransaksiListModels) {
        this.postTransaksiListModels = postTransaksiListModels;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setIdPay(String idPay) {
        this.idPay = idPay;
    }

    public void setIdSaltype(String idSaltype) {
        this.idSaltype = idSaltype;
    }

    public void setIdRefund(String idRefund) {
        this.idRefund = idRefund;
    }

    public void setTypeTrans(String typeTrans) {
        this.typeTrans = typeTrans;
    }

    public void setKetRefund(String ketRefund) {
        this.ketRefund = ketRefund;
    }

    public void setTotalTransHD(String totalTransHD) {
        this.totalTransHD = totalTransHD;
    }

    public void setIdTax(String idTax) {
        this.idTax = idTax;
    }

    public void setPayTransHD(String payTransHD) {
        this.payTransHD = payTransHD;
    }

    public void setCashBackTransHD(String cashBackTransHD) {
        this.cashBackTransHD = cashBackTransHD;
    }

    public String getIdTb() {
        return idTb;
    }

    public String getIdBusiness() {
        return idBusiness;
    }

    public String getIdCop() {
        return idCop;
    }

    public String getIdOutlet() {
        return idOutlet;
    }

    public String getIdCtm() {
        return idCtm;
    }

    public String getNoInvTransHD() {
        return noInvTransHD;
    }

    public String getDiskonTransaksi() {
        return diskonTransaksi;
    }

    public String getStatusTransHD() {
        return statusTransHD;
    }

    public ArrayList<PostTransaksiListModel> getPostTransaksiListModels() {
        return postTransaksiListModels;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdPay() {
        return idPay;
    }

    public String getIdSaltype() {
        return idSaltype;
    }

    public String getIdRefund() {
        return idRefund;
    }

    public String getTypeTrans() {
        return typeTrans;
    }

    public String getKetRefund() {
        return ketRefund;
    }

    public String getTotalTransHD() {
        return totalTransHD;
    }

    public String getIdTax() {
        return idTax;
    }

    public String getPayTransHD() {
        return payTransHD;
    }

    public String getCashBackTransHD() {
        return cashBackTransHD;
    }
}
