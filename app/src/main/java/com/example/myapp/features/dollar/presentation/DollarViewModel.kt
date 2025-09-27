package com.example.myapp.features.dollar.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.features.dollar.domain.model.DollarModel
import com.example.myapp.features.dollar.domain.usecase.CambioTipoDollarUseCase
import com.example.myapp.features.dollar.domain.usecase.UpdateDollarRatesUseCase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DollarViewModel(
    private val cambioTipoDollarUseCase: CambioTipoDollarUseCase,
    private val updateDollarRatesUseCase: UpdateDollarRatesUseCase? = null
) : ViewModel() {

    sealed class DollarUIState {
        object Loading : DollarUIState()
        object Refreshing : DollarUIState()
        object Empty : DollarUIState()
        data class Error(val message: String) : DollarUIState()
        data class Success(val data: DollarModel) : DollarUIState()
    }

    private val _uiState = MutableStateFlow<DollarUIState>(DollarUIState.Loading)
    val uiState: StateFlow<DollarUIState> = _uiState.asStateFlow()

    private var fcmToken: String? = null

    companion object {
        private const val TAG = "DollarViewModel"
    }

    init {
        viewModelScope.launch {
            fcmToken = getToken()
            getDollar()
        }
    }

    fun getDollar(isRefresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = if (isRefresh) DollarUIState.Refreshing else DollarUIState.Loading

            runCatching {
                cambioTipoDollarUseCase.invoke()
            }.onSuccess { flow ->
                flow.collect { data ->
                    if (data == null) {
                        _uiState.value = DollarUIState.Empty
                    } else {
                        _uiState.value = DollarUIState.Success(data)
                    }
                }
            }.onFailure { e ->
                Log.e(TAG, "Error cargando datos: ${e.message}", e)
                _uiState.value = DollarUIState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    suspend fun getToken(): String = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    continuation.resumeWithException(
                        task.exception ?: Exception("Error al obtener token")
                    )
                    return@addOnCompleteListener
                }
                val token = task.result
                continuation.resume(token ?: "")
            }
    }

    fun updateDollarRates(
        oficial: String? = null,
        paralelo: String? = null,
        usdt: String? = null,
        usdc: String? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = DollarUIState.Refreshing

            updateDollarRatesUseCase?.invoke(oficial, paralelo, usdt, usdc)?.let { result ->
                result.onSuccess {
                    getDollar(isRefresh = true)
                }.onFailure { exception ->
                    Log.e(TAG, "Error al actualizar: ${exception.message}", exception)
                    _uiState.value = DollarUIState.Error("Error al actualizar: ${exception.message}")
                }
            } ?: run {
                Log.w(TAG, "UpdateDollarRatesUseCase no disponible")
            }
        }
    }
}
