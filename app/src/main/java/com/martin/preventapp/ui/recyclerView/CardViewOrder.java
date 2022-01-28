package com.martin.preventapp.ui.recyclerView;

public class CardViewOrder {

        private String product;
        private int amount;
        private int delete;

        public CardViewOrder(String product, int amount) {
            this.product = product;
            this.amount = amount;
            this.delete = delete;
        }

        public String getProduct() {
            return product;
        }

        public int getAmount() {
            return amount;
        }
}
