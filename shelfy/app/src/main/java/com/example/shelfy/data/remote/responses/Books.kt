package com.example.shelfy.data.remote.responses

data class Books(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)