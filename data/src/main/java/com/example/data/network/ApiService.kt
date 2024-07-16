package com.example.data.network

import com.example.data.model.OffersResponse
import com.example.data.model.TicketsOffersResponse
import retrofit2.Response
import javax.inject.Inject

interface ApiService {
    suspend fun getOffers(offersId: String): Response<OffersResponse>
    suspend fun getTicketsOffers(ticketsOffersId: String): Response<TicketsOffersResponse>
}

internal class ApiServiceImpl @Inject constructor(
    private val api: Api
): ApiService {
    override suspend fun getOffers(offersId: String): Response<OffersResponse> =
        api.getOffers(offersId = offersId)

    override suspend fun getTicketsOffers(ticketsOffersId: String): Response<TicketsOffersResponse> =
        api.getTicketsOffers(ticketsOffersId = ticketsOffersId)
}