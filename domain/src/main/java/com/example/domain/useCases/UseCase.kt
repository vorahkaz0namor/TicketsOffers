package com.example.domain.useCases

import com.example.data.model.OffersResponse
import com.example.data.model.Resource
import com.example.data.model.TicketsOffersResponse
import com.example.data.repository.Repository
import com.example.domain.di.IODispatcher
import com.example.domain.getResponse
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

interface UseCase {
    suspend fun getOffers(): Resource<OffersResponse>
    suspend fun getTicketsOffers(): Resource<TicketsOffersResponse>
}

internal class UseCaseImpl @Inject constructor(
    private val repository: Repository,
    @IODispatcher
    private val dispatcher: CoroutineDispatcher
): UseCase {
    override suspend fun getOffers(): Resource<OffersResponse> =
        repository.getOffers().getResponse(dispatcher)

    override suspend fun getTicketsOffers(): Resource<TicketsOffersResponse> =
        repository.getTicketsOffers().getResponse(dispatcher)
}