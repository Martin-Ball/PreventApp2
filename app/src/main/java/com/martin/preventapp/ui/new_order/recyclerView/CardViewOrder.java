package com.martin.preventapp.ui.new_order.recyclerView;

public class CardViewOrder {

    private String product;
    private String amount;
    private String unit;
    private String positionItem;

    public CardViewOrder(String product2, String amount2, String unit2, String positionItem2) {
        product = product2;
        amount = amount2;
        unit = unit2;
        positionItem = positionItem2;
    }

    public String getProduct() {
        return product;
    }

    public void setTextProduct(String product2) {
        product = product2;
    }

    public String getAmount() {
        return amount;
    }

    public void setTextAmount(String amount2) {
        amount = amount2;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit2){
        unit = unit2;
    }

    public String getPositionItem() {
        return positionItem;
    }

    public void setPositionItem(String positionItem) {
        this.positionItem = positionItem;
    }
}
