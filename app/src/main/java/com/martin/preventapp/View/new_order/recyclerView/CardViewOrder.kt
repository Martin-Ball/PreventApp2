package com.martin.preventapp.View.new_order.recyclerView

class CardViewOrder(
    var product: String,
    var amount: String,
    var unit: String,
    var positionItem: String
) {
    fun setTextProduct(product2: String) {
        product = product2
    }

    fun setTextAmount(amount2: String) {
        amount = amount2
    }
}