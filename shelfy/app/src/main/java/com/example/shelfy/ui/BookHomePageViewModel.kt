package com.example.shelfy.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelfy.data.remote.responses.Books
import com.example.shelfy.data.remote.responses.Item
import com.example.shelfy.util.Resource
import com.example.shelfy.util.RetrofitInstance
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BookHomePageViewModel : ViewModel(){
    var booksUiStateRecommendation1 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    var booksUiStateRecommendation2 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    var booksUiStateRecommendation3 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    public fun getBooksRecommendation1(query : String){
        viewModelScope.launch{
            val books = RetrofitInstance.provideBooksApi().getBooks(query)
            booksUiStateRecommendation1 = Resource.Success<Books>(books)
        }
    }

    public fun getBooksRecommendation2(query : String){
        viewModelScope.launch{
            val books = RetrofitInstance.provideBooksApi().getBooks(query)
            booksUiStateRecommendation2 = Resource.Success<Books>(books)
        }
    }

    public fun getBooksRecommendation3(query : String){
        viewModelScope.launch{
            val books = RetrofitInstance.provideBooksApi().getBooks(query)
            booksUiStateRecommendation3 = Resource.Success<Books>(books)
        }
    }

    var bookUiState : Resource<Item> by mutableStateOf(Resource.Loading<Item>())

    public fun getBook(id : String){
        viewModelScope.launch{
            val book = RetrofitInstance.provideBooksApi().getBook(id)
            bookUiState = Resource.Success<Item>(book)
        }
    }

    var booksSearchUiState : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    public fun getBooksByQuery(query : String){
        viewModelScope.launch {
            val book = RetrofitInstance.provideBooksApi().getBooks(query)
            booksSearchUiState = Resource.Success<Books>(book)
        }
    }
    init{
        getBooksRecommendation1("giallo")
        getBooksRecommendation2("horror")
        getBooksRecommendation3("fantasy")
        // getBook("eu3REAAAQBAJ")
    }
}
