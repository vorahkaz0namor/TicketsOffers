package com.example.presentation.adatper

import androidx.recyclerview.widget.DiffUtil
import com.example.data.model.Offer

class OfferCallback : DiffUtil.ItemCallback<Offer>() {
    override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean =
        oldItem == newItem
}