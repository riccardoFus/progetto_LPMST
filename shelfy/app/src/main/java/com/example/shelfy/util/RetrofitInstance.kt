package com.example.shelfy.util

import com.example.shelfy.data.remote.BooksApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    fun provideBooksApi() : BooksApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.googleapis.com/books/v1/")
            .build()
            .create(BooksApi::class.java)
    }
}