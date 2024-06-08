package com.example.shelfy.data.remote

import com.example.shelfy.data.remote.responses.Books
import com.example.shelfy.data.remote.responses.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi {

    // endpoint of the Google Books API to get a list of books given
    // a certain query
    // https://www.googleapis.com/books/v1/volumes?q=”query+di+ricerca”
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query : String
    ): Books

    // endpoint of the Google Books API to get a book given
    // a certain id
    // https://www.googleapis.com/books/v1/volumes/idBook
    @GET("volumes/{id}")
    suspend fun getBook(
        @Path("id") id : String
    ) : Item
}