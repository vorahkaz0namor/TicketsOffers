package com.example.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Offer
import com.example.data.model.Resource
import com.example.data.model.TicketOffer
import com.example.domain.onFailure
import com.example.domain.onSuccess
import com.example.domain.useCases.UseCase
import com.example.presentation.model.Points
import com.example.presentation.model.UiState
import com.example.presentation.util.logState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel @Inject constructor(
    private val useCase: UseCase,
    @ApplicationContext
    private val context: Context
): ViewModel() {
    companion object {
        private const val FROM_POINT_KEY = "FROM_POINT_KEY"
    }
    private val prefs = context.getSharedPreferences("point", Context.MODE_PRIVATE)
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()
    private val _points = MutableStateFlow(Points())
    val points: StateFlow<Points>
        get() = _points.asStateFlow()
    private val _pointWasCleared = MutableStateFlow(false)
    val pointWasCleared: StateFlow<Boolean>
        get() = _pointWasCleared.asStateFlow()
    private val _offers = MutableStateFlow(emptyList<Offer>())
    val offers: StateFlow<List<Offer>>
        get() = _offers.asStateFlow()
    private val _ticketsOffers = MutableStateFlow(emptyList<TicketOffer>())
    val ticketsOffers: StateFlow<List<TicketOffer>>
        get() = _ticketsOffers.asStateFlow()

    init {
        viewModelScope.launch {
            prefs.getString(FROM_POINT_KEY, null)?.let { setDeparturePoint(it) }
            useCase.apply {
                getOffers().fetchingData { offersResponse ->
                    getTicketsOffers().fetchingData { ticketsOffersResponse ->
                        _offers.update { offersResponse.offers }
                        _ticketsOffers.update { ticketsOffersResponse.ticketsOffers }
                    }
                }
            }
        }
    }

    fun setDeparturePoint(point: String) {
        prefs.edit { putString(FROM_POINT_KEY, point) }
        _points.update { it.copy(departure = point) }
    }

    fun setArrivalPoint(point: String) {
        _points.update { it.copy(arrival = point) }
    }

    fun clearArrivalPoint() {
        _points.update { it.copy(arrival = null) }
        _pointWasCleared.update { !it }
    }

    fun reversePoints() {
        _points.update {
            Points(
                departure = it.arrival,
                arrival = it.departure
            )
        }
    }

    /**
     * Handling UseCase response depending on Success or Failure
     */
    private inline fun <T> Resource<T>
            .fetchingData(action: (value: T) -> Unit) {
        _uiState.update { UiState.Loading }
        onSuccess {
            action(it)
            _uiState.update { UiState.Success }
        }
            .onFailure {
                _uiState.update { UiState.Error }
            }
    }
}