package com.example.domain

import com.example.data.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException

inline fun <T> Resource<T>.onSuccess(action: (value: T) -> Unit): Resource<T> {
    if (this is Resource.Success) action(this.data)
    return this
}

inline fun <T> Resource<T>.onFailure(action: (error: IOException) -> Unit): Resource<T> {
    if (this is Resource.Failure) action(error)
    return this
}

internal suspend fun <T> Resource<T>.getResponse(dispatcher: CoroutineDispatcher): Resource<T> {
    return withContext(dispatcher) {
        when (this@getResponse) {
            is Resource.Success -> {
                Resource.Success(this@getResponse.data)
            }

            is Resource.Failure -> {
                Resource.Failure(this@getResponse.error)
            }
        }
    }
}