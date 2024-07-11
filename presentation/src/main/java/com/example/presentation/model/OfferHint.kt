package com.example.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.data.model.DelegateAdapterItem
import com.example.resources.R

data class OfferHint(
    @DrawableRes
    val image: Int,
    @StringRes
    val name: Int
): DelegateAdapterItem {
    companion object {
        val hintsList =
            listOf(
                OfferHint(
                    image = R.drawable.ic_hard_route_hint_48,
                    name = R.string.hard_route
                ),
                OfferHint(
                    image = R.drawable.ic_anywhere_hint_48,
                    name = R.string.anywhere
                ),
                OfferHint(
                    image = R.drawable.ic_weekend_hint_48,
                    name = R.string.weekend
                ),
                OfferHint(
                    image = R.drawable.ic_burnings_hint_48,
                    name = R.string.burning_tickets
                )
            )

        val advicesList =
            listOf(
                OfferHint(
                    image = R.drawable.im_popular_one_40,
                    name = R.string.istanbul
                ),
                OfferHint(
                    image = R.drawable.im_popular_two_40,
                    name = R.string.sochi
                ),
                OfferHint(
                    image = R.drawable.im_popular_three_40,
                    name = R.string.phuket
                )
            )
    }

    override fun id(): Any = image

    override fun content(): Any = this
}