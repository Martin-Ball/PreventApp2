package com.martin.preventapp.ui.recyclerView;

public class CardViewOrder {

        private String product;
        private int amount;

        public CardViewOrder(String product, int amount) {
            this.product = product;
            this.amount = amount;
        }

        public String getProduct() {
            return product;
        }

        public int getAmount() {
            return amount;
        }
    }
