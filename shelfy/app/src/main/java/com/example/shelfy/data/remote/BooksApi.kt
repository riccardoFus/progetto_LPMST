package com.example.shelfy.data.remote

import com.example.shelfy.data.remote.responses.Books
import com.example.shelfy.data.remote.responses.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi {
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query : String
    ): Books

    @GET("volumes/{id}")
    suspend fun getBook(
        @Path("id") id : String
    ) : Item
}