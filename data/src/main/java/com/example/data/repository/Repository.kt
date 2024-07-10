package com.example.data.repository

import com.example.data.Constants
import com.example.data.handleAPICall
import com.example.data.model.OffersResponse
import com.example.data.model.Resource
import com.example.data.network.ApiService
import javax.inject.Inject

interface Repository {
    suspend fun getOffers(): Resource<OffersResponse>
}

internal class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
): Repository {
    override suspend fun getOffers(): Resource<OffersResponse> =
        handleAPICall { apiService.getOffers(Constants.OFFERS_ID) }
}