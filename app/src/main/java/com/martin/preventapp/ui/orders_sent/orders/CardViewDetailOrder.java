package com.martin.preventapp.ui.orders_sent.orders;

public class CardViewDetailOrder {

    private String product;
    private String amount;
    private String unit;

    public CardViewDetailOrder(String product2, String amount2, String unit2) {
        product = product2;
        amount = amount2;
        unit = unit2;
    }

    public void changeTextProduct(String product2) {
        this.product = product2;
    }

    public void changeTextAmount(String amount2) {
        this.amount = amount2;
    }

    public String getProduct() {
        return product;
    }

    public String getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
