package com.example.presentation.adatper

import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.TicketOffer
import com.example.resources.R
import com.example.presentation.databinding.RecommCardLayoutBinding
import com.example.presentation.util.ticketOfferColor

class TicketOfferViewHolder(
    private val binding: RecommCardLayoutBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(ticketOffer: TicketOffer) {
        binding.apply {
            recommIcon.setCardBackgroundColor(
                root.context.getColor(ticketOfferColor(ticketOffer.id))
            )
            pointName.text = ticketOffer.title
            pointTimeRange.text = ticketOffer.timeRangeToString()
            pointPrice.text = root.context.getString(
                /* resId = */ R.string.ticket_offer_price,
                /* ...formatArgs = */ ticketOffer.price.value.toString()
            )
        }
    }
}