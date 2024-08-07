package com.example.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.example.presentation.databinding.FragmentStubBinding
import com.example.presentation.util.viewBinding
import com.example.presentation.util.viewScopeWithRepeat
import com.example.presentation.viewmodel.TicketsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

class StubFragment : Fragment(R.layout.fragment_stub) {
    private val binding by viewBinding(FragmentStubBinding::bind)
    private val viewModel: TicketsViewModel by activityViewModels()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewScopeWithRepeat {
            binding.backIcon.apply {
                viewModel.sheetIsShown.mapLatest {
                    visibility =
                        if (it)
                            View.VISIBLE
                        else
                            View.GONE
                }
                    .stateIn(this@viewScopeWithRepeat)
                setOnClickListener { findNavController().navigateUp() }
            }
        }
    }
}