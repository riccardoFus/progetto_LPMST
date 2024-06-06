package com.example.shelfy.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.shelfy.data.db.Note
import com.example.shelfy.data.db.Readlist
import com.example.shelfy.data.db.Review
import com.example.shelfy.data.db.User
import com.example.shelfy.data.remote.responses.Books
import com.example.shelfy.data.remote.responses.Item
import com.example.shelfy.navigation.Screens
import com.example.shelfy.util.Resource
import com.example.shelfy.util.RetrofitInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.security.MessageDigest


// Checks if a given email is valid based on a predefined regular expression.
fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return emailRegex.matches(email)
}

// Checks if a given password is valid based on a predefined regular expression.
fun isValidPassword(password: String): Boolean {
    val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
    return passwordRegex.matches(password)
}

// Generates a SHA-256 hash for a given input string.
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

    var openingDone: Boolean by mutableStateOf(false)

    // A mutable state variable that holds the state of the book recommendations 1
    var booksUiStateRecommendation1 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())

    // Function to fetch books by a query
    private fun getBooksRecommendation1(query : String){
        // Launch a coroutine in the viewModelScope to perform the network request
        viewModelScope.launch{
            // Fetch the books from the API based on the provided query
            val books = RetrofitInstance.provideBooksApi().getBooks(query)
            // Update the state to reflect the successful data fetch
            booksUiStateRecommendation1 = Resource.Success<Books>(books)
        }
    }


    var booksUiStateRecommendation2 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    private fun getBooksRecommendation2(query : String){
        viewModelScope.launch{
            val books = RetrofitInstance.provideBooksApi().getBooks(query)
            booksUiStateRecommendation2 = Resource.Success<Books>(books)
        }
    }


    var booksUiStateRecommendation3 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())
    private fun getBooksRecommendation3(query : String){
        viewModelScope.launch{
            val books = RetrofitInstance.provideBooksApi().getBooks(query)
            booksUiStateRecommendation3 = Resource.Success<Books>(books)
        }
    }

    var booksUiStateRecommendation4 : Resource<Books> by mutableStateOf(Resource.Loading<Books>())

    // Function to fetch books by a query
    private fun getBooksRecommendation4(query : String){
        // Launch a coroutine in the viewModelScope to perform the network request
        viewModelScope.launch{
            // Fetch the books from the API based on the provided query
            val books = RetrofitInstance.provideBooksApi().getBooks(query)
            // Update the state to reflect the successful data fetch
            booksUiStateRecommendation4 = Resource.Success<Books>(books)
        }
    }

    // A mutable state variable that holds the state of a single book item
    var bookUiState : Resource<Item> by mutableStateOf(Resource.Loading<Item>())

    // Function to fetch a book by its ID
    fun getBook(id : String){
        // Launch a coroutine in the viewModelScope to perform the network request
        viewModelScope.launch{
            // Fetch the book from the API based on the provided ID
            val book = RetrofitInstance.provideBooksApi().getBook(id)
            // Update the state to reflect the successful data fetch
            bookUiState = Resource.Success<Item>(book)
        }
    }

    // A mutable state variable that holds the state of the books search results
    var booksSearchUiState : Resource<Books> by mutableStateOf(Resource.Loading<Books>())

    // Function to fetch books based on a search query
    fun getBooksByQuery(query : String){
        // Launch a coroutine in the viewModelScope to perform the network request
        viewModelScope.launch {
            // Fetch the books from the API based on the provided query
            val book = RetrofitInstance.provideBooksApi().getBooks(query)
            // Update the state to reflect the successful data fetch
            booksSearchUiState = Resource.Success<Books>(book)
        }
    }

    // A mutable state variable that holds the user ID
    var userId: String by mutableStateOf("")
    // A mutable state variable that holds the username
    var username: String by mutableStateOf("")
    // A mutable state variable that indicates whether the login is done
    var loginDone : Boolean by mutableStateOf(false)
    // A mutable state variable that indicates whether the user is already signed in
    var alreadySignedIn : Boolean by mutableStateOf(false)
    // A mutable state variable that indicates whether the username already exist
    var alreadyUsernameExist : Boolean by mutableStateOf(false)


    // Function to check if a user with a given email already exists in Firebase Firestore
    private fun checkEmailAlreadyExists(email: String): Boolean{
        var result by mutableStateOf(false)
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()

        dB.collection("Users").whereEqualTo("email", email).get().addOnSuccessListener {documents ->
            if(documents.isEmpty) result = true
        }

        return result
    }

    private fun checkUsernameAlreadyExists(username: String): Boolean{
        var result by mutableStateOf(false)
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()

        dB.collection("Users").whereEqualTo("username", username).get().addOnSuccessListener {documents ->
            if(documents.isEmpty) result = true
        }

        return result
    }

    // Function to sign in a user with email and password
    fun signInUser(
        email: String,
        password: String,
        username: String = ""
    ){
        var result by mutableStateOf(false)
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()

        dB.collection("Users").whereEqualTo("email", email).get().addOnSuccessListener {documents ->
            if(!documents.isEmpty) alreadySignedIn = true
            if(!result && !alreadySignedIn){
                dB.collection("Users").whereEqualTo("username", username).get().addOnSuccessListener {documents ->
                    if(!documents.isEmpty) alreadyUsernameExist = true
                    if(!result && !alreadyUsernameExist){
                        this.username = username
                        FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener{
                                // Add the user to the database with the provided details
                                addUser(username, email, password,
                                    FirebaseAuth.getInstance().currentUser?.uid ?: ""
                                )
                                // Perform login with the provided email and password
                                login(email, password, "SignIn")
                            }
                            .addOnFailureListener{
                            }
                    }
                }
            }
        }
    }

    // Function to log out the current user
    fun logout(){
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        userId = ""
        username = ""
        loginDone = false
    }

    var showDialogLogin : Boolean by mutableStateOf(false)

    // Function to authenticate user login using email and password.
    fun login(email: String, password: String, from: String = ""){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        // Query Firestore to find user with matching email
        dB.collection("Users").whereEqualTo("email", email).get()
            .addOnSuccessListener { documents ->
                var found : Boolean = false
                for (document in documents) {
                    if (document.get("password") == password) {
                        found = true
                        // Sign in with Firebase Authentication
                        FirebaseAuth
                            .getInstance()
                            .signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener {
                            }
                            .addOnFailureListener {
                            }
                        // Query Firestore again to fetch additional user data
                        dB.collection("Users").whereEqualTo("email", email).get()
                            .addOnSuccessListener { documents1 ->
                                for (document2 in documents1) {
                                    username = document.get("username").toString()
                                    userId = document.id
                                }
                                if(from == "SignIn"){
                                    addReadlist("Libreria", userId)
                                }
                                loginDone = true
                            }
                    }
                }
                if(!found) showDialogLogin = true
            }
    }

    // Function to add user in Firebase Firestore
    private fun addUser(username: String, email: String, password: String, uid: String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbUsers: CollectionReference = dB.collection("Users")
        val user = User(username, email, password, uid)
        dbUsers.add(user)
            .addOnSuccessListener {}
            .addOnFailureListener {}
    }

    // Wrapper function to create a review in Firebase Firestore
    fun createReviewInFirebase(
        id : String,
        stars : Int,
        text: String = "",
    ){
        addReview(id, username, stars, text)
    }

    // A mutable state variable that holds the sum of the votes of a given book
    var sumReviews : Int by mutableIntStateOf(0)

    // A mutable state variable that holds the number of votes and the average vote of a given book
    var numberAndMediaReviews : Pair<Int, Double> by mutableStateOf(Pair<Int, Double>(0, 0.0))

    // A mutable state variable that checks if the list of reviews of a given book is updated or not
    var reviewsUpdated by mutableStateOf(false)

    // function to get reviews of a given book, it updates the number of votes and the average vote of
    // the book
    fun getReviews(bookId : String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbReviews  = dB.collection("Reviews")
        dbReviews.get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    val list = it.documents
                    // counter of the books
                    var count = 0
                    for(document in list){
                        val review : Review? = document.toObject(Review::class.java)
                        if (review != null && review.bookId == bookId) {
                            // sum of all the votes of the reviews
                            sumReviews = sumReviews.plus(review.stars)
                            count++
                        }
                    }
                    // update of the number of reviews and the average vote
                    if(sumReviews != 0) {
                        numberAndMediaReviews = Pair<Int, Double>(count, sumReviews.toDouble() / count.toDouble())
                    }else{
                        numberAndMediaReviews = Pair<Int, Double>(0, 0.0)
                    }
                    sumReviews = 0
                }
            }
    }

    // A mutable state variable that holds the list of reviews of a given book
    var reviews = mutableStateListOf<Review>()

    // Function to get reviews of a given book, and also gives information the creator of the review
    fun getReviewsPlusUser(bookId : String) {
            val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        reviews.clear()
            dB.collection("Reviews").whereEqualTo("bookId", bookId).get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        for (document in documents) {
                            reviews.add(
                                Review(
                                    document.get("username").toString(),
                                    document.getLong("stars")?.toInt() ?: 0,
                                    document.get("desc").toString()
                                )
                            )
                        }

                        reviewsUpdated = true
                    }
        }
    }

    // Function to add a review of a user for a book in Firebase Firestpre
    private fun addReview(id: String, username: String, stars: Int, text: String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbReviews  = dB.collection("Reviews")
        val review = Review(id, username, stars, text)
        dbReviews.add(review)
            .addOnSuccessListener {}
            .addOnFailureListener {}
    }

    // Function to add a note in Firebase Firestore
    fun addNote(userId: String, text: String, bookId: String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbNotes  = dB.collection("Notes")
        val note = Note(userId, text, bookId)
        if (userId != "") {
            dbNotes.add(note)
                .addOnSuccessListener {}
                .addOnFailureListener {}
        }
    }

    // A mutable state variable that checks if the library is added in Firebase Firestore
    var libraryAdded : Boolean by mutableStateOf(false)

    // A mutable state variable that checks if the library is updated in Firebase Firestore
    var libraryUpdated: Boolean by mutableStateOf(false)

    // Function to add a readlist for a user in Firebase Firestore
    var alreadyReadlistExist : Boolean by mutableStateOf(false)
    fun addReadlist(name : String, userId : String){
        val dB : FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbReadlist = dB.collection("Readlists")
        var result by mutableStateOf(false)
        dbReadlist.whereEqualTo("name", name).whereEqualTo("userId", userId).get().addOnSuccessListener {
            if(it.documents.isEmpty()) result = true;
            else alreadyReadlistExist = true
            if(result){
                val readlist = Readlist(name = name, userId = userId, emptyList())
                if (userId != "") {
                    dbReadlist.add(readlist)
                        .addOnSuccessListener {
                            readlistsUpdated = false
                        }
                        .addOnFailureListener {}
                }
            }
        }
    }

    // A mutable state variable that checks if a readlist is updated or not in Firebase Firestore
    var readlistsUpdated by mutableStateOf(false)

    // Function to add a book in a readlist
    fun addToReadlist(bookId: Item?, userId: String, readlist: String){
        val dB : FirebaseFirestore = FirebaseFirestore.getInstance()
        dB.collection("Readlists").whereEqualTo("userId", userId).get().addOnSuccessListener {
            documents -> if(!documents.isEmpty){
                for(document in documents){
                    if(document.get("name") == readlist) {
                        val readlistDb : Readlist? = document.toObject(Readlist::class.java)

                        // check if book is already contained in readlist
                        var contain : Boolean = false
                        if (readlistDb != null) {
                            for(item in readlistDb.content){
                                if(item.id == bookId!!.id){
                                    contain = true
                                }
                            }
                        }

                        if(!contain){
                            dB.collection("Readlists").document(document.id)
                                .update("content", FieldValue.arrayUnion(bookId)).addOnSuccessListener {
                                    readlistsUpdated = false
                                    if(readlist == "Libreria"){
                                        libraryUpdated = false
                                    }
                                }.addOnFailureListener {
                                }
                        }
                    }
                }
            }
        }
    }

    // A mutable state variable that holds a list of readlists of the user
    var readlists = mutableStateListOf<Readlist>()

    // Function to get all the readlist of a given user
    fun getReadlists(){
        readlists.clear()
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbReadlist = dB.collection("Readlists")
        dbReadlist
            .whereEqualTo("userId", userId)
            .get()
                .addOnSuccessListener {
                    val list = it.documents
                    for (document in list) {
                        val readlist: Readlist? = document.toObject(Readlist::class.java)
                        if (readlist != null) {
                            readlists.add(readlist)
                        }
                    }
                }
    }

    // Function to delete a given book from a given readlist
    fun deleteBookFromReadlist(readlist: String, bookId: String){
        var documentId: String
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        dB.collection("Readlists").whereEqualTo("userId", userId).get().addOnSuccessListener{documents ->
            for(document in documents){
                if(document.get("name") == readlist){
                    documentId = document.id
                    val rl = document.toObject<Readlist>()
                    var newList = mutableListOf<Item>()
                    newList = rl.content as MutableList<Item>
                    newList.removeIf{it.id == bookId}
                    dB.collection("Readlists").document(documentId).update("content", newList)
                        .addOnSuccessListener {
                            readlistsUpdated = false
                            if(readlist == "Libreria") libraryUpdated = false
                        }
                        .addOnFailureListener {  }
                }
            }
        }
    }

    fun deleteReadlist(readlist: String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        dB.collection("Readlists")
            .whereEqualTo("name",readlist).get().addOnSuccessListener{documents ->
            for(document in documents){
                if(document.get("userId") == userId){
                    dB.collection("Readlists")
                        .document(document.id).delete().addOnSuccessListener {
                            readlistsUpdated = false
                        }
                }
            }
        }
    }

    // A mutable state variable that holds a list of Item (Book)
    var itemList = mutableStateListOf<Item>()

    // Function to get all the books in the library of the user
    fun getElementsLibrary(userId : String){
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dbReadlist  = dB.collection("Readlists")
        dbReadlist
            .whereEqualTo("userId", userId)
            .whereEqualTo("name", "Libreria")
            .get()
            .addOnSuccessListener {
                val list = it.documents
                for(document in list){
                    val readlist : Readlist? = document.toObject(Readlist::class.java)
                    if (readlist != null) {
                        for(books in readlist.content){
                            itemList.add(books)
                        }
                    }
                }
            }
    }

    // Function to sort the list of readlists in a given way
    fun sortReadlists(by: String){
        if(by == "cre")
            readlists.sortBy { it.name }
        if(by == "dec")
            readlists.sortByDescending { it.name }
    }

    // A mutable state variable that holds the content of a note
    var note : String by mutableStateOf("")

    // Function to update a note of a user for a given book
    fun updateNote(userId: String, text: String, bookId: String){
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

    // Function to get a note of the user for a given book
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

    // A mutable state variable that checks if a note already exists in Firebase Firestore, given user
    // and book
    var noteAlreadyExists : Boolean by mutableStateOf(false)

    // Function to check if a note of a user for a given book already exists
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

    fun deleteUser(navController : NavHostController) {
        val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
        dB.collection("Reviews").whereEqualTo("username", username).get().addOnSuccessListener {
            documents ->
            for(document in documents){
                dB.collection("Reviews")
                    .document(document.id).delete().addOnSuccessListener {
                    }
            }
            dB.collection("Notes").whereEqualTo("userId", userId).get().addOnSuccessListener {
                    documents ->
                for(document in documents){
                    dB.collection("Notes")
                        .document(document.id).delete().addOnSuccessListener {
                        }
                }
                dB.collection("Readlists").whereEqualTo("userId", userId).get().addOnSuccessListener {
                        documents ->
                    for(document in documents){
                        dB.collection("Readlists")
                            .document(document.id).delete().addOnSuccessListener {
                            }
                    }
                    dB.collection("Users").document(userId).delete()
                    FirebaseAuth.getInstance().currentUser?.delete()?.addOnSuccessListener {
                        userId = ""
                        username = ""
                        loginDone = false
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate(Screens.LOGIN_SCREEN)
                    }
                }
            }
        }
    }

    var page: String by mutableStateOf("Homepage")

    // Initialization of the AppViewModel, it creates the book recommendation lists
    init{
        getBooksRecommendation1("thriller")
        getBooksRecommendation2("avventura")
        getBooksRecommendation3("fantastico")
        getBooksRecommendation4("giallo")
    }
}
