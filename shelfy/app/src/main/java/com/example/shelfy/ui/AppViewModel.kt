package com.example.shelfy.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelfy.data.db.Note
import com.example.shelfy.data.db.Readlist
import com.example.shelfy.data.db.Review
import com.example.shelfy.data.db.User
import com.example.shelfy.data.remote.responses.Books
import com.example.shelfy.data.remote.responses.Item
import com.example.shelfy.util.Resource
import com.example.shelfy.util.RetrofitInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.security.MessageDigest

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return emailRegex.matches(email)
}

fun isValidPassword(password: String): Boolean {
    val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
    return passwordRegex.matches(password)
}

fun sha256(input: String): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
    val hexString = StringBuilder()
    for (byte in bytes) {
        val hex = Integer.toHexString(0xff and byte.toInt())
        if (hex.length == 1) {
            hexString.append('0')
        }
        hexString.append(hex)
    }
    return hexString.toString()
}

class AppViewModel : ViewModel(){
    var booksUiStateRecommendation1 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    var booksUiStateRecommendation2 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    var booksUiStateRecommendation3 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    var userId: String by mutableStateOf("")
    var sumReviews : Int by mutableIntStateOf(0)
    var numberAndMediaReviews : Pair<Int, Double> by mutableStateOf(Pair<Int, Double>(0, 0.0))
    var loginDone : Boolean by mutableStateOf(false)
    var alreadySignedIn : Boolean by mutableStateOf(false)
    var note : String by mutableStateOf("")
    var libraryAdded : Boolean by mutableStateOf(false)
    var noteAlreadyExists : Boolean by mutableStateOf(false)
    private fun getBooksRecommendation1(query : String){
        viewModelScope.launch{
            val books = RetrofitInstance.provideBooksApi().getBooks(query)
            booksUiStateRecommendation1 = Resource.Success<Books>(books)
        }
    }

    private fun getBooksRecommendation2(query : String){
        viewModelScope.launch{
            val books = RetrofitInstance.provideBooksApi().getBooks(query)
            booksUiStateRecommendation2 = Resource.Success<Books>(books)
        }
    }

    private fun getBooksRecommendation3(query : String){
        viewModelScope.launch{
            val books = RetrofitInstance.provideBooksApi().getBooks(query)
            booksUiStateRecommendation3 = Resource.Success<Books>(books)
        }
    }

    var bookUiState : Resource<Item> by mutableStateOf(Resource.Loading<Item>())

    fun getBook(id : String){
        viewModelScope.launch{
            val book = RetrofitInstance.provideBooksApi().getBook(id)
            bookUiState = Resource.Success<Item>(book)
        }
    }

    var booksSearchUiState : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    fun getBooksByQuery(query : String){
        viewModelScope.launch {
            val book = RetrofitInstance.provideBooksApi().getBooks(query)
            booksSearchUiState = Resource.Success<Books>(book)
        }
    }

    fun signInUser(
        email: String,
        password: String,
        username: String = ""
    ){
        alreadySignedIn = runBlocking { checkEmailAlreadyExists(email) }
        if (!alreadySignedIn){
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener{
                    addUser(username, email, password)
                    login(email, password)
                    // addReadlist("Libreria", userId)
                }
                .addOnFailureListener{
                }
        }
    }

    fun createReviewInFirebase(
        id : String,
        stars : Int,
        text: String = "",
    ){
        addReview(id, userId, stars, text)
    }

    fun logout(){
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        userId = ""
        loginDone = false
    }

    fun login(email: String, password: String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
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
                                FirebaseAuth
                                    .getInstance()
                                    .createUserWithEmailAndPassword(email, password)
                            }
                        dB.collection("Users").whereEqualTo("email", email).get()
                            .addOnSuccessListener { documents1 ->
                                for (document2 in documents1) {
                                    userId = document.id
                                }
                            }
                        loginDone = true
                    }
                }
            }
    }

    private fun addUser(username: String, email: String, password: String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbUsers: CollectionReference = dB.collection("Users")
        val user = User(username, email, password)
        dbUsers.add(user)
            .addOnSuccessListener {}
            .addOnFailureListener {}
    }


    private fun addReview(id: String, userId: String, stars: Int, text: String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbReviews  = dB.collection("Reviews")
        val review = Review(id, userId, stars, text)
        dbReviews.add(review)
            .addOnSuccessListener {}
            .addOnFailureListener {}
    }
    fun addNota(userId: String, text: String, bookId: String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbNotes  = dB.collection("Notes")
        val note = Note(userId, text, bookId)
        if (userId != "") {
            dbNotes.add(note)
                .addOnSuccessListener {}
                .addOnFailureListener {}
        }
    }

    fun addReadlist(name : String, userId : String){
        val dB : FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbReadlist = dB.collection("Readlists")
        val readlist = Readlist(name = name, userId = userId, emptyList())
        if (userId != "") {
            libraryAdded = true
            dbReadlist.add(readlist)
                .addOnSuccessListener {}
                .addOnFailureListener {}
        }
    }

    fun addToReadlist(bookId : String, userId : String){
        val dB : FirebaseFirestore = FirebaseFirestore.getInstance()
        dB.collection("Readlists").whereEqualTo("userId", userId).get().addOnSuccessListener {
            documents -> if(!documents.isEmpty){
            System.err.println("Valp")
            for(document in documents){
                        dB.collection("Readlists").document(document.id).update("content", FieldValue.arrayUnion(bookId)).addOnSuccessListener {
                                System.err.println("Va")
                        }.addOnFailureListener {
                            System.err.println("Non va")

                        }
                }
            }
        }
    }

    fun updateNota(userId: String, text: String, bookId: String){
        var id = ""
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        dB.collection("Notes").whereEqualTo("userId", userId).get().addOnSuccessListener(){
            documents -> if(!documents.isEmpty){
                for(document in documents){
                    if(document.get("bookId").toString() == bookId){
                        note = text
                        id = document.id
                        System.err.println("Va")
                        dB.collection("Notes").document(id).update("text", text).addOnSuccessListener { }.addOnFailureListener { }
                    }
                }
            }
        }
    }

    fun getNota(userId: String, bookId: String?){
        var found: Boolean = false
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        dB.collection("Notes").whereEqualTo("userId", userId).get().addOnSuccessListener {
            documents -> if(!documents.isEmpty){
                for(document in documents){
                    if(document.get("bookId").toString() == bookId) {
                        note = document.get("text").toString()
                        found = true
                    }
                    else {
                        if(!found)
                            note = ""
                    }
                }
            }
        }
    }

    private fun checkEmailAlreadyExists(email: String): Boolean{
        var result by mutableStateOf(false)
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()

        dB.collection("Users").whereEqualTo("email", email).get().addOnSuccessListener {documents ->
            if(documents.isEmpty) result = true
        }

        return result
    }

  fun checkNoteAlreadyInserted(userId: String, bookId: String){
        var found = false
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        dB.collection("Notes").whereEqualTo("userId", userId).get().addOnSuccessListener {
            documents -> if(!documents.isEmpty){
                for(document in documents){
                    if(document.get("bookId") == bookId) {
                        found = true
                        }
                    }
                }
            noteAlreadyExists = found
        }

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
                            sumReviews = sumReviews.plus(review.stars)
                            conta++
                        }
                    }
                    if(sumReviews != 0) {
                        numberAndMediaReviews = Pair<Int, Double>(conta, sumReviews.toDouble() / conta.toDouble())
                    }else{
                        numberAndMediaReviews = Pair<Int, Double>(0, 0.0)
                    }
                    sumReviews = 0
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
