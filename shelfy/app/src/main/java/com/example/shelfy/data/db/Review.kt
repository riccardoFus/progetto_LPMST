package com.example.shelfy.data.db

data class Review(
    val bookId : String,
    val username : String,
    val stars : Int,
    val desc : String
){
    constructor(): this("", "", 0, "")
    constructor(username: String, stars: Int, desc: String ): this("", username, stars, desc)
}