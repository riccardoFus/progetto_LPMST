package com.example.shelfy.data.db

import com.example.shelfy.data.remote.responses.Item

data class Readlist (
    val name : String,
    val userId : String,
    val content : List<String>
    // val image : Image
){
    constructor(): this("","", emptyList())
}