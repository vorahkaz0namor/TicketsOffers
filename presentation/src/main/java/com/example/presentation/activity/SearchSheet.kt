package com.example.presentation.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.example.presentation.adatper.AdviceAdapterDelegate
import com.example.presentation.adatper.HintAdapterDelegate
import com.example.presentation.adatper.MainCompositeAdapter
import com.example.presentation.databinding.SearchSheetLayoutBinding
import com.example.presentation.model.OfferHint
import com.example.presentation.util.hideKeyboard
import com.example.presentation.util.layoutSizeAdjust
import com.example.presentation.util.viewBinding
import com.example.presentation.util.viewScopeWithRepeat
import com.example.presentation.viewmodel.TicketsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

class SearchSheet : BottomSheetDialogFragment(R.layout.search_sheet_layout) {
    private val binding by viewBinding(SearchSheetLayoutBinding::bind)
    private val viewModel: TicketsViewModel by activityViewModels()
    private val hintsAdapter by lazy {
        MainCompositeAdapter.Builder()
            .add(HintAdapterDelegate())
            .build()
    }
    private val advicesAdapter by lazy {
        MainCompositeAdapter.Builder()
            .add(AdviceAdapterDelegate())
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        subscribe()
        setupListeners()
    }

    private fun init() {
        binding.offersList.adapter = hintsAdapter
        binding.advicesList.adapter = advicesAdapter
        viewScopeWithRepeat {
            requireActivity().layoutSizeAdjust(binding.root)
            binding.searchCard.apply {
                viewModel.points.value.apply {
                    fromField.text = departure
                    toField.setText(arrival)
                }
            }
            hintsAdapter.submitList(OfferHint.hintsList)
            advicesAdapter.submitList(OfferHint.advicesList)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun subscribe() {
        viewScopeWithRepeat {
            viewModel.pointWasCleared.mapLatest {
                binding.searchCard.toField.setText(
                    viewModel.points.value.arrival
                )
            }
                .stateIn(this)
        }
    }

    private fun setupListeners() {
        viewScopeWithRepeat {
            binding.searchCard.apply {
                toField.addTextChangedListener { point ->
                    point?.let { viewModel.setArrivalPoint(it.toString()) }
                }
                toField.setOnKeyListener { _, keyCode, _ ->
                    if (keyCode == KeyEvent.KEYCODE_NAVIGATE_NEXT ||
                        keyCode == KeyEvent.KEYCODE_ENTER ||
                        keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER)
                            findNavController().navigate(R.id.ticketsOffersFragment)
                    true
                }
                clearIcon.setOnClickListener {
                    viewModel.clearArrivalPoint()
                }
            }
        }
    }

    override fun onStop() {
        hideKeyboard(binding.root)
        super.onStop()
    }

    companion object {
        const val TAG = "SearchSheet"
    }
}