package com.example.presentation.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.example.presentation.adatper.MainCompositeAdapter
import com.example.presentation.adatper.TicketOfferAdapterDelegate
import com.example.presentation.databinding.FragmentTicketsOffersBinding
import com.example.presentation.util.dateRepresentation
import com.example.presentation.util.dayOfWeekRepresentation
import com.example.presentation.util.toOffsetDateTime
import com.example.presentation.util.viewBinding
import com.example.presentation.util.viewScopeWithRepeat
import com.example.presentation.viewmodel.TicketsViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateSelector
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import java.time.Instant
import java.time.OffsetDateTime

class TicketsOffersFragment : Fragment(R.layout.fragment_tickets_offers) {
    private val binding by viewBinding(FragmentTicketsOffersBinding::bind)
    private val viewModel: TicketsViewModel by activityViewModels()
    private val adapter by lazy {
        MainCompositeAdapter.Builder()
            .add(TicketOfferAdapterDelegate())
            .build()
    }
    private val takeoffDatePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Select takeoff date")
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setCalendarConstraints(
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())
                .build()
        )
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        subscribe()
        setupListeners()
    }

    private fun init() {
        binding.recommsList.adapter = adapter
        displayTakeoffDate(OffsetDateTime.now())
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
        viewScopeWithRepeat {
            binding.detailButtons.takeoffDate.setOnClickListener {
                takeoffDatePicker.show(childFragmentManager, null)
            }
            takeoffDatePicker.addOnPositiveButtonClickListener {
                displayTakeoffDate(toOffsetDateTime(takeoffDatePicker.selection))
            }
        }
    }

    private fun displayTakeoffDate(date: OffsetDateTime) {
        binding.detailButtons.date.text = dateRepresentation(date)
        binding.detailButtons.weekDay.text =
            getString(
                R.string.takeoff_week_day,
                dayOfWeekRepresentation(date)
            )
    }
}