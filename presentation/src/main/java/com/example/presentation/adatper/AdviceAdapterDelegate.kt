package com.example.presentation.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.AdviceCardBinding
import com.example.presentation.model.OfferHint

class AdviceAdapterDelegate(
    private val onInteractionListener: OnInteractionListener
) : DelegateAdapter<OfferHint, AdviceViewHolder>(OfferHint::class.java) {
    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        AdviceViewHolder(
            AdviceCardBinding.inflate(
                /* inflater = */ LayoutInflater.from(parent.context),
                /* parent = */ parent,
                /* attachToParent = */ false
            ),
            onInteractionListener
        )

    override fun bindViewHolder(model: OfferHint, viewHolder: AdviceViewHolder) {
        viewHolder.bind(model)
    }
}