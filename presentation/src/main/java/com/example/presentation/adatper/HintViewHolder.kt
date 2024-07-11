package com.example.presentation.adatper

import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.HintCardBinding
import com.example.presentation.model.OfferHint

class HintViewHolder(
    private val binding: HintCardBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(hint: OfferHint) {
        binding.apply {
            hintImage.setImageResource(hint.image)
            hintName.text = root.context.getString(hint.name)
        }
    }
}