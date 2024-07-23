package com.example.presentation.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.TicketOffer
import com.example.presentation.databinding.RecommCardLayoutBinding

class TicketOfferAdapterDelegate : DelegateAdapter<TicketOffer, TicketOfferViewHolder>(TicketOffer::class.java) {
    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        TicketOfferViewHolder(
            RecommCardLayoutBinding.inflate(
                /* inflater = */ LayoutInflater.from(parent.context),
                /* parent = */ parent,
                /* attachToParent = */ false
            )
        )

    override fun bindViewHolder(model: TicketOffer, viewHolder: TicketOfferViewHolder) {
        viewHolder.bind(model)
    }
}