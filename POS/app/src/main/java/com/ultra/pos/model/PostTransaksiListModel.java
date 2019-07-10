package com.ultra.pos.model;

import android.support.design.internal.Experimental;

import androidx.browser.browseractions.BrowserActionsIntent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PostTransaksiListModel implements Serializable {

    @SerializedName("idproduk")
    @Expose
    private String idProduk;

    @SerializedName("idvariant")
    @Expose
    private String idVariant;

    @SerializedName("idpaket")
    @Expose
    private String idPaket;

    @SerializedName("qty")
    @Expose
    private String quantity;

    @SerializedName("harga_satuan")
    @Expose
    private String hargaSatuan;

    @SerializedName("idtax")
    @Expose
    private String idTax;

    public PostTransaksiListModel(String idProduk, String idVariant, String idPaket, String quantity, String hargaSatuan, String idTax){
        this.idProduk = idProduk;
        this.idVariant = idVariant;
        this.idPaket = idPaket;
        this.quantity = quantity;
        this.hargaSatuan = hargaSatuan;
        this.idTax = idTax;
    }

    public PostTransaksiListModel(List<String> dataIdProduk, List<String> dataNamaProduk, List<String> dataIdVariant, List<String> dataNamaVariant, List<String> dataHargaVariant, List<String> dataIdTax) {

    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public void setIdVariant(String idVariant) {
        this.idVariant = idVariant;
    }

    public void setIdPaket(String idPaket) {
        this.idPaket = idPaket;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setHargaSatuan(String hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public void setIdTax(String idTax) {
        this.idTax = idTax;
    }

    public String getIdProduk() {
        return idProduk;
    }

    public String getIdVariant() {
        return idVariant;
    }

    public String getIdPaket() {
        return idPaket;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getHargaSatuan() {
        return hargaSatuan;
    }

    public String getIdTax() {
        return idTax;
    }
}
