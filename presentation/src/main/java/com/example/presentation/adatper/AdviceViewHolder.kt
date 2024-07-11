package com.example.presentation.adatper

import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.AdviceCardBinding
import com.example.presentation.model.OfferHint

class AdviceViewHolder(
    private val binding: AdviceCardBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(advice: OfferHint) {
        binding.apply {
            adviceIcon.setImageResource(advice.image)
            pointName.text = root.context.getString(advice.name)
        }
    }
}