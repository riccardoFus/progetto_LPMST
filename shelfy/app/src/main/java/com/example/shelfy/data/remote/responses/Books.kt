package com.example.shelfy.data.remote.responses

data class Books(
    val items: List<Item> = emptyList(),
    val kind: String = "",
    val totalItems: Long = 0
)