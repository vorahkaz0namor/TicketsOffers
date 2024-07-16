package com.example.data.network

import com.example.data.model.TicketsOffersResponse
import com.example.data.model.OffersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("uc")
    suspend fun getOffers(
        @Query("id") offersId: String
    ): Response<OffersResponse>

    @GET("uc")
    suspend fun getTicketsOffers(
        @Query("id") ticketsOffersId: String
    ): Response<TicketsOffersResponse>
}