package com.example.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.example.presentation.adatper.MainCompositeAdapter
import com.example.presentation.adatper.TicketOfferAdapterDelegate
import com.example.presentation.databinding.FlightDateBinding
import com.example.presentation.databinding.FragmentTicketsOffersBinding
import com.example.presentation.util.dateRepresentation
import com.example.presentation.util.dayOfWeekRepresentation
import com.example.presentation.util.milliToOffsetDateTime
import com.example.presentation.util.viewBinding
import com.example.presentation.util.viewScopeWithRepeat
import com.example.presentation.viewmodel.TicketsViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import java.time.OffsetDateTime

class TicketsOffersFragment : Fragment(R.layout.fragment_tickets_offers) {
    private val binding by viewBinding(FragmentTicketsOffersBinding::bind)
    private val viewModel: TicketsViewModel by activityViewModels()
    private val adapter by lazy {
        MainCompositeAdapter.Builder()
            .add(TicketOfferAdapterDelegate())
            .build()
    }
    private val datePicker = { title: String ->
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(title)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointForward.now())
                    .build()
            )
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            customNavigateUp()
        }
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
        viewScopeWithRepeat {
            viewModel.dates.mapLatest {
                binding.detailButtons.apply {
                    comebackDateNotSelect.isVisible = it.comeback == null
                    comebackDateCard.root.isVisible = it.comeback != null
                    displayFlightDate(it.comeback, comebackDateCard)
                    displayFlightDate(it.takeoff, takeoffDateCard)
                }
            }
                .stateIn(this)
        }
    }

    private fun setupListeners() {
        viewScopeWithRepeat {
            binding.setPointsCard.apply {
                backIcon.setOnClickListener {
                    customNavigateUp()
                }
                reverseIcon.setOnClickListener {
                    viewModel.reversePoints()
                }
            }
        }
        viewScopeWithRepeat {
            val takeoffDatePicker = datePicker(getString(R.string.select_takeoff_date))
            binding.detailButtons.takeoffDate.setOnClickListener {
                takeoffDatePicker.show(childFragmentManager, null)
            }
            takeoffDatePicker.addOnPositiveButtonClickListener {
                viewModel.setTakeoffDate(milliToOffsetDateTime(takeoffDatePicker.selection))
            }
        }
        viewScopeWithRepeat {
            val comebackDatePicker = datePicker(getString(R.string.select_comeback_date))
            binding.detailButtons.comebackDate.setOnClickListener {
                comebackDatePicker.show(childFragmentManager, null)
            }
            comebackDatePicker.addOnPositiveButtonClickListener {
                viewModel.setComebackDate(milliToOffsetDateTime(comebackDatePicker.selection))
            }
        }
    }

    private fun displayFlightDate(
        dateTime: OffsetDateTime?,
        dateBinding: FlightDateBinding
    ) {
        val displayDate = dateTime ?: OffsetDateTime.now()
        dateBinding.date.text = dateRepresentation(displayDate)
        dateBinding.weekDay.text =
            getString(
                R.string.week_day,
                dayOfWeekRepresentation(displayDate)
            )
    }

    private fun customNavigateUp() {
        viewModel.clearFlightDates()
        findNavController().navigateUp()
    }
}