package com.martin.preventapp.ui.new_order.recyclerView;

import android.widget.ImageView;

public class CardViewOrder {

    private String product;
    private String amount;

    public CardViewOrder(String product2, String amount2) {
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
