package com.example.shelfy.util

import com.example.shelfy.data.remote.BooksApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    // building of a retrofit
    fun provideBooksApi() : BooksApi{
        return Retrofit.Builder()
            // format of the result of the API
            .addConverterFactory(GsonConverterFactory.create())
            // base URL of the Google Books API
            .baseUrl("https://www.googleapis.com/books/v1/")
            .build()
            // given a Retrofit Instance, it create a BooksApi object and returns it
            .create(BooksApi::class.java)
    }
}