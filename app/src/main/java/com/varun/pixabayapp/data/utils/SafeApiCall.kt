package com.varun.pixabayapp.data.utils

import com.varun.pixabayapp.domain.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException


/**
 * Generic util function to get [Resource] of suspend block result.
 *
 * @param apiCall: Suspend block which needs to runs safely
 * @param coroutineDispatcher: Context with the block will be run with
 */
suspend inline fun <T> safeApiCallWithCoroutine(coroutineDispatcher: CoroutineDispatcher, crossinline apiCall: suspend () -> T) = withContext(coroutineDispatcher) {
    try {
        Resource.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is HttpException -> Resource.Failure(throwable, throwable.code())
            is IOException -> Resource.Failure(throwable, 0)
            else -> Resource.Failure(throwable, -1)
        }
    }
}