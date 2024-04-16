package com.example.shelfy.repository

import com.example.shelfy.data.remote.BooksApi
import com.example.shelfy.data.remote.responses.Books
import com.example.shelfy.data.remote.responses.Item
import com.example.shelfy.util.Resource

class BooksRepository (
    private val api : BooksApi
){
    suspend fun getBooks(query : String): Resource<Books> {
        val response = try{
            api.getBooks(query)
        }catch(e : Exception){
            return Resource.Error("Errore sconosciuto.")
        }
        return Resource.Success(response)
    }

    suspend fun getBook(id : String) : Resource<Item> {
        val response = try{
            api.getBook(id)
        }catch(e : Exception){
            return Resource.Error("Errore sconosciuto")
        }
        return Resource.Success(response)
    }
}