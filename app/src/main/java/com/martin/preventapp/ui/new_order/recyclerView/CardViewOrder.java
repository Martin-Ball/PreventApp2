package com.martin.preventapp.ui.new_order.recyclerView;

public class CardViewOrder {

    private String product;
    private String amount;

    public CardViewOrder(String text1, String text2) {
        product = text1;
        amount = text2;
    }

    public void changeTextProduct(String text) {
        product = text;
    }

    public String getProduct() {
        return product;
    }

    public String getAmount() {
        return amount;
    }
}
