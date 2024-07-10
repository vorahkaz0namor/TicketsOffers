package com.example.presentation.adatper

import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.Offer
import com.example.resources.R
import com.example.presentation.databinding.OfferCardBinding
import com.example.presentation.util.offerImage

class OfferViewHolder(
    private val binding: OfferCardBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(offer: Offer) {
        binding.apply {
            pointImage.setImageResource(offerImage(offer.id))
            pointDescription.text = offer.title
            pointName.text = offer.town
            pointPrice.text = root.context.getString(
                R.string.offer_price,
                offer.price.value.toString()
            )
        }
    }
}