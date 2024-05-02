package com.example.shelfy.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelfy.data.db.Note
import com.example.shelfy.data.db.Review
import com.example.shelfy.data.db.User
import com.example.shelfy.data.remote.responses.Books
import com.example.shelfy.data.remote.responses.Item
import com.example.shelfy.util.Resource
import com.example.shelfy.util.RetrofitInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


class BookHomePageViewModel : ViewModel(){
    var booksUiStateRecommendation1 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    var booksUiStateRecommendation2 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    var booksUiStateRecommendation3 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    var userId: String by mutableStateOf("")
    var sum : Int by mutableIntStateOf(0)
    var tot : Pair<Int, Double> by mutableStateOf(Pair<Int, Double>(0, 0.0))
    var login : Boolean by mutableStateOf(false)
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
        if (!checkEmailAlreadyExists(email) && !checkUsernameAlreadyExists(username)){
            addUser(username, email, password)
            login(email, password)
        }
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
        userId = ""
        login = false
    }

    fun login(email: String, password: String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        System.out.println("Sono arrivato qua")
        val dbUsers: CollectionReference = dB.collection("Users")
        if(email.contains("@")) {
            dB.collection("Users").whereEqualTo("email", email).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        if (document.get("password") == password) {
                            FirebaseAuth
                                .getInstance()
                                .signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener {
                                }
                                .addOnFailureListener {
                                }
                            dB.collection("Users").whereEqualTo("email", email).get()
                                .addOnSuccessListener { documents1 ->
                                    for (document2 in documents1) {
                                        userId = document.id
                                    }
                                }
                            login = true
                        }
                    }
                }
        }
        else {
            dB.collection("Users").whereEqualTo("username", email).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        if (document.get("password") == password) {
                            FirebaseAuth
                                .getInstance()
                                .signInWithEmailAndPassword(email, password)

                                .addOnCompleteListener {
                                }
                                .addOnFailureListener {
                                }
                            dB.collection("Users").whereEqualTo("username", email).get()
                                .addOnSuccessListener { documents1 ->
                                    for (document2 in documents1) {
                                        userId = document.id
                                    }
                                }
                            login = true
                        }
                    }
                }
        }
    }

    fun getLoginValue(): Boolean{
        return login
    }
    fun addUser(username: String, email: String, password: String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbUsers: CollectionReference = dB.collection("Users")
        val user = User(username, email, password)
        dbUsers.add(user).addOnSuccessListener {
        }.addOnFailureListener {
        }
    }


    fun addReview(id: String, stars: Int, text: String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbRecensioni  = dB.collection("Reviews")
        val review = Review(id, stars, text)
        dbRecensioni.add(review).addOnSuccessListener {
        }.addOnFailureListener {
        }
    }
    fun addNota(userId: String, text: String, bookId: String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbRecensioni  = dB.collection("Notes")
        val note = Note(userId, text, bookId)
        if (userId != "") {
            dbRecensioni.add(note).addOnSuccessListener {
            }.addOnFailureListener {
            }
        }
    }
    fun checkEmailAlreadyExists(email: String): Boolean{
        var result = false
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbRecensioni  = dB.collection("Users")

        dB.collection("Users").whereEqualTo("email", email).get().addOnSuccessListener {documents ->
            if(documents.isEmpty) result = true
        }
        return result
    }

    fun checkUsernameAlreadyExists(username: String): Boolean{
        var result = false
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbRecensioni  = dB.collection("Users")

        dB.collection("Users").whereEqualTo("username", username).get().addOnSuccessListener {documents ->
            if(documents.isEmpty) result = true
        }
        return result
    }

    fun getUser(): String {
        return userId
    }

    fun getReviews(bookId : String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbReviews  = dB.collection("Reviews")
        dbReviews.get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    val list = it.documents
                    var conta = 0
                    for(document in list){
                        val review : Review? = document.toObject(Review::class.java)
                        if (review != null && review.bookId == bookId) {
                            sum = sum.plus(review.stars)
                            conta++
                        }
                    }
                    if(sum != 0) {
                        tot = Pair<Int, Double>(conta, sum.toDouble() / conta.toDouble())
                    }else{
                        tot = Pair<Int, Double>(0, 0.0)
                    }
                    sum = 0
                }
            }
    }



    init{
        getBooksRecommendation1("giallo")
        getBooksRecommendation2("horror")
        getBooksRecommendation3("fantasy")
        // getBook("eu3REAAAQBAJ")
    }
}
