package com.example.shelfy.data.remote.responses

data class Offer(
    val finskyOfferType: Int = 0,
    val listPrice: ListPriceX = ListPriceX(),
    val retailPrice: RetailPrice = RetailPrice()
)