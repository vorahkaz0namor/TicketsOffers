package com.example.presentation.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.example.presentation.adatper.MainCompositeAdapter
import com.example.presentation.adatper.TicketOfferAdapterDelegate
import com.example.presentation.databinding.FragmentTicketsOffersBinding
import com.example.presentation.util.viewBinding
import com.example.presentation.util.viewScopeWithRepeat
import com.example.presentation.viewmodel.TicketsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

class TicketsOffersFragment : Fragment(R.layout.fragment_tickets_offers) {
    private val binding by viewBinding(FragmentTicketsOffersBinding::bind)
    private val viewModel: TicketsViewModel by activityViewModels()
    private val adapter by lazy {
        MainCompositeAdapter.Builder()
            .add(TicketOfferAdapterDelegate())
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        subscribe()
        setupListeners()
    }

    private fun init() {
        binding.recommsList.adapter = adapter
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun subscribe() {
        viewScopeWithRepeat {
            binding.setPointsCard.apply {
                viewModel.points.mapLatest { points ->
                    fromField.text = points.departure
                    toField.text = points.arrival
                }
                    .stateIn(this@viewScopeWithRepeat)
            }
        }
        viewScopeWithRepeat {
            viewModel.ticketsOffers
                .mapLatest(adapter::submitList)
                .stateIn(this)
        }
    }

    private fun setupListeners() {
        viewScopeWithRepeat {
            binding.setPointsCard.apply {
                backIcon.setOnClickListener {
                    findNavController().navigateUp()
                }
                reverseIcon.setOnClickListener {
                    viewModel.reversePoints()
                }
            }
        }
    }
}