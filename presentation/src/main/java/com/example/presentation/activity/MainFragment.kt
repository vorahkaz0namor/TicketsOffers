package com.example.presentation.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.example.presentation.R
import com.example.presentation.adatper.MainCompositeAdapter
import com.example.presentation.adatper.OfferAdapterDelegate
import com.example.presentation.databinding.FragmentMainBinding
import com.example.presentation.model.UiState.Companion.error
import com.example.presentation.model.UiState.Companion.loading
import com.example.presentation.model.UiState.Companion.success
import com.example.presentation.util.hideKeyboard
import com.example.presentation.util.layoutSizeAdjust
import com.example.presentation.util.viewBinding
import com.example.presentation.util.viewScopeWithRepeat
import com.example.presentation.viewmodel.TicketsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: TicketsViewModel by activityViewModels()
    private val adapter by lazy {
        MainCompositeAdapter.Builder()
            .add(OfferAdapterDelegate())
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        subscribe()
        setupListeners()
    }

    private fun init() {
        viewScopeWithRepeat {
            requireActivity().layoutSizeAdjust(binding.root)
            binding.startSearch.foregroundCard.apply {
                viewModel.points.value.apply {
                    fromField.setText(departure)
                    toField.setText(arrival)
                }
            }
        }
        binding.startSearch.offersList.adapter = adapter
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun subscribe() {
        viewModel.apply {
            viewScopeWithRepeat {
                uiState.mapLatest { state ->
                    binding.apply {
                        progressBarView.progressBar.isVisible = state.loading
                        errorView.errorMessage.isVisible = state.error
                        startSearch.root.isVisible = state.success
                    }
                }
                    .stateIn(this)
            }
            viewScopeWithRepeat {
                offers.mapLatest(adapter::submitList)
                    .stateIn(this)
            }
        }
    }

    private fun setupListeners() {
        viewScopeWithRepeat {
            binding.startSearch.foregroundCard.fromField.addTextChangedListener { point ->
                point?.let { viewModel.setDeparturePoint(it.toString()) }
            }
            binding.startSearch.foregroundCard.toField.addTextChangedListener { point ->
                point?.let { viewModel.setArrivalPoint(it.toString()) }
            }
        }
    }

    override fun onStop() {
        hideKeyboard(binding.root)
        super.onStop()
    }
}