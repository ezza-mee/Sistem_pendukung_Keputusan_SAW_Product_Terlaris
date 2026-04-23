package com.main.models.entity;

public class listTransactionProduct {
    public int idProduct;
    public String nameProduct;
    public int quantity;
    public int priceProduct;
    public int subPrice;
    public String periode;

    public listTransactionProduct(int idProduct, String nameProduct, int quantity, int priceProduct, int subPrice, String periode) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.quantity = quantity;
        this.priceProduct = priceProduct;
        this.subPrice = subPrice;
        this.periode = periode;
    }

    public int getIdProduct() {
        return idProduct;
    }
}
