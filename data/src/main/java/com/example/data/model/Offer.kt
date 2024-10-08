package com.example.data.model

data class Offer(
    val id: Int,
    val title: String,
    val town: String,
    val price: Price
): DelegateAdapterItem {
    override fun id(): Any = id

    override fun content(): Any = this
}