package com.example.data.model

import com.google.gson.annotations.SerializedName

data class TicketOffer(
    val id: Int,
    val title: String,
    @SerializedName("time_range")
    val timeRange: List<String>,
    val price: Price
): DelegateAdapterItem {
    override fun id(): Any = id

    override fun content(): Any = this

    override fun toString(): String =
        "id = $id, title = $title, price = ${price.value}\n"

    fun timeRangeToString() =
        buildString { timeRange.map { append("$it ") } }
}
