package com.example.shelfy.util


// Definition of a sealed class Resource that represents the state of a resource with a generic type T
sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    // Success subclass representing a successful state, contains the requested data
    class Success<T>(data: T) : Resource<T>(data)

    // Error subclass representing an error state, contains an error message and optionally the data
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    // Loading subclass representing a loading state, optionally contains the data
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
