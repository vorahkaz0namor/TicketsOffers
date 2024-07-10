package com.example.data

import com.example.data.model.Resource
import com.example.data.model.ResponseModel
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

internal suspend fun <T : ResponseModel> handleAPICall(
    apiCall: suspend () -> Response<T>,
): Resource<T> {
    return try {
        apiCall.invoke().handleAPIResponse()
    } catch (e: Exception) {
        e.printStackTrace()
        return when (e) {
            is UnknownHostException -> isFailure()
            else -> isFailure()
        }
    }
}

private fun <T : ResponseModel> Response<T>.handleAPIResponse(): Resource<T> {
    val responseBody = body() ?: return isFailure()
    if (isSuccess()) {
        return Resource.Success(responseBody)
    }
    return isFailure()
}

private fun <T : ResponseModel> Response<T>.isSuccess(): Boolean = isSuccessful

private fun <T : ResponseModel> isFailure(): Resource<T> =
    Resource.Failure(IOException())