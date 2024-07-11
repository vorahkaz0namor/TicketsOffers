package com.example.presentation.adatper

import android.util.SparseArray
import android.view.ViewGroup
import androidx.core.util.forEach
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.DelegateAdapterItem

class MainCompositeAdapter(
    private val delegates: SparseArray<DelegateAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>>
) : ListAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>(DelegateAdapterItemDiffCallback()) {
    override fun getItemViewType(position: Int): Int {
        delegates.forEach { key, _ ->
            if (delegates[key].modelClass == getItem(position).javaClass)
                return key
        }
        throw NullPointerException("Can not get viewType for position $position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegates[viewType].createViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates[getItemViewType(position)]
            ?.bindViewHolder(getItem(position), holder)
            ?: throw NullPointerException("Can not find adapter for position $position")
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        delegates[holder.itemViewType].onViewRecycled(holder)
        super.onViewRecycled(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        delegates[holder.itemViewType].onViewDetachedFromWindow(holder)
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        delegates[holder.itemViewType].onViewAttachedToWindow(holder)
        super.onViewAttachedToWindow(holder)
    }

    class Builder {
        private var count: Int = 0
        private val delegates: SparseArray<DelegateAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>> =
            SparseArray()

        @Suppress("UNCHECKED_CAST")
        fun add(delegateAdapter: DelegateAdapter<out DelegateAdapterItem, *>): Builder {
            (delegateAdapter as? DelegateAdapter<DelegateAdapterItem, RecyclerView.ViewHolder>)?.let {
                delegates.put(count++, it)
            }
            return this
        }

        fun build(): MainCompositeAdapter {
            require(count != 0) { "Should be registered at least one adapter!" }
            return MainCompositeAdapter(delegates)
        }
    }
}