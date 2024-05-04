package com.example.shelfy.data.db

data class User(
    val username : String,
    val email : String,
    val password : String
){
    constructor(): this("","", "")
}
