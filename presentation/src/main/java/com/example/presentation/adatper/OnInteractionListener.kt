package com.example.presentation.adatper

interface OnInteractionListener {
    fun setArrivalPointFromAdvice(point: String)
}

class OnInteractionListenerImpl(
    private val callback: (String) -> Unit
): OnInteractionListener {
    override fun setArrivalPointFromAdvice(point: String) {
        callback(point)
    }
}