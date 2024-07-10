package com.example.presentation.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.data.model.Offer
import com.example.presentation.databinding.OfferCardBinding

class OfferAdapter : ListAdapter<Offer, OfferViewHolder>(OfferCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder =
        OfferViewHolder(
            OfferCardBinding.inflate(
                /* inflater = */ LayoutInflater.from(parent.context),
                /* parent = */ parent,
                /* attachToParent = */ false
            )
        )

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}