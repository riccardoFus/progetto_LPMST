package com.example.shelfy.data.db

data class Review(
    val bookId : String,
    val stars : Int,
    val desc : String
){
    constructor(): this("",0, "")
}
