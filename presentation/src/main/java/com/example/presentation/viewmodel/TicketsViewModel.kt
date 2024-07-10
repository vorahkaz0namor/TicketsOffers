package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Offer
import com.example.data.model.Resource
import com.example.domain.onFailure
import com.example.domain.onSuccess
import com.example.domain.useCases.UseCase
import com.example.presentation.model.Points
import com.example.presentation.model.UiState
import com.example.presentation.util.logState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel @Inject constructor(
    private val useCase: UseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()
    private val _points = MutableStateFlow(Points())
    val points: StateFlow<Points>
        get() = _points.asStateFlow()
    private val _offers = MutableStateFlow(emptyList<Offer>())
    val offers: StateFlow<List<Offer>>
        get() = _offers.asStateFlow()

    init {
        viewModelScope.launch {
            useCase.getOffers().fetchingData { data ->
                _offers.update { data.offers }
            }
        }
    }


    fun setDeparturePoint(point: String) {
        _points.update { it.copy(departure = point) }
    }

    fun setArrivalPoint(point: String) {
        _points.update { it.copy(arrival = point) }
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