package com.openclassroom.joiefull.ui.screens.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassroom.joiefull.data.repository.CatalogueRepository
import com.openclassroom.joiefull.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val repository: CatalogueRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            when (val result = repository.loadCatalogue()) {
                is DataState.Success -> _uiState.value = SplashUiState.Ready
                is DataState.Error -> _uiState.value = SplashUiState.Error(result.message)
                else -> _uiState.value = SplashUiState.Loading
            }
        }
    }
}


sealed interface SplashUiState {
    object Loading : SplashUiState
    object Ready : SplashUiState
    data class Error(val message: String) : SplashUiState
}