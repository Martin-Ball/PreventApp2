package com.martin.preventapp.ui.recyclerView;

public class CardViewOrder {

        private int imagen;
        private String product;
        private int amount;

        public CardViewOrder(String product, int amount) {
            this.product = product;
            this.amount = amount;
        }

        public String getNombre() {
            return product;
        }

        public int getVisitas() {
            return amount;
        }

        public int getImagen() {
            return imagen;
        }
    }
