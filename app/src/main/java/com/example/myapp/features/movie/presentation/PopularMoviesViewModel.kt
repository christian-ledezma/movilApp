package com.example.myapp.features.movie.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.features.movie.domain.model.MovieModel
import com.example.myapp.features.movie.domain.usecase.FetchPopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PopularMoviesViewModel(
    private val fetchPopularMovies: FetchPopularMoviesUseCase
) : ViewModel () {

    companion object {
        private const val TAG = "PopularMoviesViewModel"
    }//
    sealed class UiState {
        object Loading : UiState()
        data class Success(val movies: List<MovieModel>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        Log.d(TAG, "ViewModel inicializado")
        fetchPopularMovies()
    }//

    fun fetchPopularMovies() {
        Log.d(TAG, "Iniciando fetchPopularMovies")//
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UiState.Loading
            Log.d(TAG, "Estado cambiado a Loading")//
            val result = fetchPopularMovies.invoke()
            Log.d(TAG, "UseCase ejecutado, procesando resultado")//
            result.fold(
                onSuccess = { movies ->
                    Log.d(TAG, "Éxito: ${movies.size} películas obtenidas")
                    _state.value = UiState.Success(movies)//
                },
                onFailure = {exception ->
                    Log.e(TAG, "Error al obtener películas", exception)
                    _state.value = UiState.Error(exception.message ?: "Error desconocido")
                }
            )
        }
    }
}