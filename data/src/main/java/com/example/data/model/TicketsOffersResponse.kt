package com.example.data.model

import com.google.gson.annotations.SerializedName

data class TicketsOffersResponse(
    @SerializedName("tickets_offers")
    val ticketsOffers: List<TicketOffer>
): ResponseModel