package com.example.shelfy.repository

import com.example.shelfy.data.remote.BooksApi
import com.example.shelfy.data.remote.responses.Books
import com.example.shelfy.data.remote.responses.Item
import com.example.shelfy.util.Resource

class BooksRepository (
    private val api : BooksApi
){
    // async function to get a list of books from Google Books API
    // given a certain query
    suspend fun getBooks(query : String): Resource<Books> {
        val response = try{
            api.getBooks(query)
        }catch(e : Exception){
            return Resource.Error("Errore sconosciuto.")
        }
        return Resource.Success(response)
    }

    // async function to get a book from Google Books API
    // given a certain id
    suspend fun getBook(id : String) : Resource<Item> {
        val response = try{
            api.getBook(id)
        }catch(e : Exception){
            return Resource.Error("Errore sconosciuto")
        }
        return Resource.Success(response)
    }
}