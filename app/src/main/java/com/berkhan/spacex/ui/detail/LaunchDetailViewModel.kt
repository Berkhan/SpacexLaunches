package com.berkhan.spacex.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkhan.spacex.LaunchQuery
import com.berkhan.spacex.data.NetworkResponse
import com.berkhan.spacex.domain.usecase.LaunchUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchDetailViewModel @Inject constructor(private val launchUseCases: LaunchUseCases) :
    ViewModel() {

    private val _uiStateLiveData: MutableLiveData<LaunchDetailUiState> =
        MutableLiveData(LaunchDetailUiState.initial())
    val uiStateLiveData: LiveData<LaunchDetailUiState> get() = _uiStateLiveData

    fun getLaunch(launchId: String) {
        viewModelScope.launch {
            launchUseCases.getLaunchUseCase(launchId).collectLatest {
                handleNetworkResponse(it)
            }
        }
    }

    private fun handleNetworkResponse(response: NetworkResponse<LaunchQuery.Launch>) {
        when (response) {
            NetworkResponse.Loading -> {
                _uiStateLiveData.value = _uiStateLiveData.value?.copy(isLoading = true)
            }
            is NetworkResponse.Error -> {
                _uiStateLiveData.value = _uiStateLiveData.value?.copy(
                    errorMessage = response.exception.message,
                    isLoading = false,
                    data = null
                )
            }
            is NetworkResponse.Success -> {
                _uiStateLiveData.value = _uiStateLiveData.value?.copy(
                    data = response.result,
                    errorMessage = null,
                    isLoading = false
                )
            }
        }
    }

}