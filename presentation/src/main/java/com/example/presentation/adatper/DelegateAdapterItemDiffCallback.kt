package com.example.presentation.adatper

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.data.model.DelegateAdapterItem
import com.example.data.model.Offer

class DelegateAdapterItemDiffCallback : DiffUtil.ItemCallback<DelegateAdapterItem>() {
    override fun areItemsTheSame(oldItem: DelegateAdapterItem, newItem: DelegateAdapterItem): Boolean =
        (oldItem::class == newItem::class) && (oldItem.id() == newItem.id())

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DelegateAdapterItem, newItem: DelegateAdapterItem): Boolean =
        oldItem.content() == newItem.content()
}