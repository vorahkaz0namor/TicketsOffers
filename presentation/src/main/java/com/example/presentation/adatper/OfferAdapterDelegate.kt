package com.example.presentation.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.Offer
import com.example.presentation.databinding.OfferCardBinding

class OfferAdapterDelegate : DelegateAdapter<Offer, OfferViewHolder>(Offer::class.java) {
    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        OfferViewHolder(
            OfferCardBinding.inflate(
                /* inflater = */ LayoutInflater.from(parent.context),
                /* parent = */ parent,
                /* attachToParent = */ false
            )
        )

    override fun bindViewHolder(model: Offer, viewHolder: OfferViewHolder) {
        viewHolder.bind(model)
    }
}