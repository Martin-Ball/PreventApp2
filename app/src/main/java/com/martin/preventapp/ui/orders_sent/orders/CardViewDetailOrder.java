package com.martin.preventapp.ui.orders_sent.orders;

public class CardViewDetailOrder {

    private String product;
    private String amount;

    public CardViewDetailOrder(String product2, String amount2) {
        product = product2;
        amount = amount2;
    }

    public void changeTextProduct(String product2) {
        product = product2;
    }

    public void changeTextAmount(String amount2) {
        amount = amount2;
    }

    public String getProduct() {
        return product;
    }

    public String getAmount() {
        return amount;
    }

}
