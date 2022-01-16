package com.martin.preventapp.ui.recyclerView;

public class CardViewOrder {

        private int imagen;
        private String nombre;
        private int visitas;

        public CardViewOrder(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }

        public int getVisitas() {
            return visitas;
        }

        public int getImagen() {
            return imagen;
        }
    }
