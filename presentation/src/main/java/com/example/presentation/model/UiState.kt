package com.example.presentation.model

sealed interface UiState {
    data object Loading: UiState
    data object Error: UiState
    data object Success: UiState

    companion object {
        val <T: UiState> T.loading: Boolean
            get() = this is Loading
        val <T: UiState> T.error: Boolean
            get() = this is Error
        val <T: UiState> T.success: Boolean
            get() = this is Success
    }
}