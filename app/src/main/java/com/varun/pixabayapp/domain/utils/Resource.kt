package com.varun.pixabayapp.domain.utils

/**
 * A generic class to wrap object with specific state.
 */
sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Failure<T>(val throwable: Throwable, val code: Int) : Resource<T>()
}