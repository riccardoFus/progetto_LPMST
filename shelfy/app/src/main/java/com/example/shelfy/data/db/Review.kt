package com.example.shelfy.data.db

data class Review(
    val bookId : String,
    val userId : String,
    val stars : Int,
    val desc : String
){
    constructor(): this("", "", 0, "")
    constructor(userId: String, stars: Int, desc: String ): this("", userId, stars, desc)
}