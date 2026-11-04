package com.example.tp08_firestore.model

data class Product(
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val price: Double = 0.0
) {
    companion object {
        const val COLLECTION = "Produtos"
        const val NAME = "nome"
        const val CATEGORY = "categoria"
        const val PRICE = "preco"
    }
}
