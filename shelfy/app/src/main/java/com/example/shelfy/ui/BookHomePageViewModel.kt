package com.example.shelfy.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelfy.data.db.Nota
import com.example.shelfy.data.db.Recensione
import com.example.shelfy.data.db.Utente
import com.example.shelfy.data.remote.responses.Books
import com.example.shelfy.data.remote.responses.Item
import com.example.shelfy.util.Resource
import com.example.shelfy.util.RetrofitInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


class BookHomePageViewModel : ViewModel(){
    var booksUiStateRecommendation1 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    var booksUiStateRecommendation2 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    var booksUiStateRecommendation3 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    var sum: Int? = 0
    var total = 0;
    var chiamate = 0
    var userId: String = ""
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
    

    fun createUserInFirebase(
        email: String,
        password: String,
        confirmPassword: String = "",
        username: String = ""
    ){
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
            }
            .addOnFailureListener{
            }
        addUser(username, email, password)
    }

    fun createReviewInFirebase(
        id : String,
        stars : Int,
        text: String = "",
    ){
        addReview(id, stars, text)
    }

    fun logout(){
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signOut()

    }

    fun login(email: String, password: String){
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
            }
            .addOnFailureListener {
            }
    }

    fun addUser(username: String, email: String, password: String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbUsers: CollectionReference = dB.collection("Users")

        val user = Utente(username, email, password)

        dbUsers.add(user).addOnSuccessListener {
        }.addOnFailureListener {
        }

        dB.collection("Users").whereEqualTo("email", email).get().addOnSuccessListener {documents ->
            for (document in documents) {
                userId = document.id
            }
        }
    }


    fun addReview(id: String, stars: Int, text: String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbRecensioni  = dB.collection("Reviews")
        val review = Recensione(id, stars, text)
        dbRecensioni.add(review).addOnSuccessListener {
        }.addOnFailureListener {
        }
    }
    fun addNota(userId: String, text: String, bookId: String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbRecensioni  = dB.collection("Notes")
        val note = Nota(userId, text, bookId)
        dbRecensioni.add(note).addOnSuccessListener {
        }.addOnFailureListener {
        }
    }

    fun getUser(): String {
        return userId
    }

    fun getReviews(bookId : String): Pair<Int, Double>{
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbRecensioni  = dB.collection("Recensioni")
        /*val query: Query = dbRecensioni.orderByChild("bookId").equalTo(bookId)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                chiamate++
                for(ds in snapshot.children){
                    total++
                    sum = sum?.plus(ds.child("stars").getValue(Int::class.java)!!)
                    System.out.println("Ciao " + total.toString() + " " + sum + " stavo cercando le recensioni per " + bookId + " e ho trovato " + ds.child("desc").getValue(String::class.java) + " e questa è la chiamata numero " + chiamate)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })*/
        var tot = Pair<Int, Double>(0,0.0)
        if(sum != 0) {
            tot = Pair<Int, Double>(total, sum!!.toDouble() / total)
        }
        total = 0
        sum = 0
        return tot
    }



    init{
        getBooksRecommendation1("giallo")
        getBooksRecommendation2("horror")
        getBooksRecommendation3("fantasy")
        // getBook("eu3REAAAQBAJ")
    }
}
