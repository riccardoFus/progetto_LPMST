package com.example.shelfy.data.remote.responses

data class SaleInfo(
    val buyLink: String = "",
    val country: String = "",
    val isEbook: Boolean = false,
    val listPrice: ListPrice = ListPrice(),
    val offers: List<Offer> = emptyList(),
    val retailPrice: RetailPriceX = RetailPriceX(),
    val saleability: String = ""
)