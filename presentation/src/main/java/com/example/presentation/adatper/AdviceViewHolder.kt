package com.example.presentation.adatper

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.AdviceCardBinding
import com.example.presentation.model.OfferHint

class AdviceViewHolder(
    private val binding: AdviceCardBinding,
    private val onInteractionListener: OnInteractionListener
): RecyclerView.ViewHolder(binding.root) {
    fun bind(advice: OfferHint) {
        binding.apply {
            adviceIcon.setImageResource(advice.image)
            pointName.text = root.context.getString(advice.name)
            setOnClickListeners(root, pointName, pointDescription)
        }
    }

    private fun setOnClickListeners(vararg view: View) {
        view.map {
            it.setOnClickListener {
                onInteractionListener.setArrivalPointFromAdvice(binding.pointName.text.toString())
            }
        }
    }
}