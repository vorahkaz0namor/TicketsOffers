package com.example.presentation.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.HintCardBinding
import com.example.presentation.model.OfferHint

class HintAdapterDelegate : DelegateAdapter<OfferHint, HintViewHolder>(OfferHint::class.java) {
    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        HintViewHolder(
            HintCardBinding.inflate(
                /* inflater = */ LayoutInflater.from(parent.context),
                /* parent = */ parent,
                /* attachToParent = */ false
            )
        )

    override fun bindViewHolder(model: OfferHint, viewHolder: HintViewHolder) {
        viewHolder.bind(model)
    }
}