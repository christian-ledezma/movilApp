package com.example.myapp.features.movie.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.features.movie.domain.model.MovieModel
import com.example.myapp.features.movie.domain.usecase.FetchPopularMoviesUseCase
import com.example.myapp.features.movie.domain.usecase.ToggleLikeUseCase
import com.example.myapp.features.movie.domain.usecase.GetLikedMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PopularMoviesViewModel(
    private val fetchPopularMovies: FetchPopularMoviesUseCase,
    private val toggleLikeUseCase: ToggleLikeUseCase,
    private val getLikedMoviesUseCase: GetLikedMoviesUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "PopularMoviesViewModel"
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(
            val movies: List<MovieModel>,
            val likedMovies: Set<String>
        ) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _allMovies = MutableStateFlow<List<MovieModel>>(emptyList())

    init {
        Log.d(TAG, "ViewModel inicializado")
        fetchPopularMovies()
        observeLikedMovies()
    }

    private fun observeLikedMovies() {
        viewModelScope.launch {
            combine(
                _allMovies,
                getLikedMoviesUseCase()
            ) { movies: List<MovieModel>, likedTitles: List<String> ->
                if (movies.isNotEmpty()) {
                    val likedSet = likedTitles.toSet()

                    // Ordenar: primero los liked, después los no liked
                    val sortedMovies = movies.sortedWith { movie1: MovieModel, movie2: MovieModel ->
                        val isMovie1Liked = likedSet.contains(movie1.title)
                        val isMovie2Liked = likedSet.contains(movie2.title)

                        when {
                            isMovie1Liked && !isMovie2Liked -> -1 // movie1 primero
                            !isMovie1Liked && isMovie2Liked -> 1  // movie2 primero
                            else -> 0 // mantener orden original
                        }
                    }

                    _state.value = UiState.Success(sortedMovies, likedSet)
                }
            }.collect { /* No hacer nada aquí */ }
        }
    }

    fun fetchPopularMovies() {
        Log.d(TAG, "Iniciando fetchPopularMovies")
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UiState.Loading
            Log.d(TAG, "Estado cambiado a Loading")

            val result = fetchPopularMovies.invoke()
            Log.d(TAG, "UseCase ejecutado, procesando resultado")

            result.fold(
                onSuccess = { movies: List<MovieModel> ->
                    Log.d(TAG, "Éxito: ${movies.size} películas obtenidas")
                    _allMovies.value = movies
                },
                onFailure = { exception: Throwable ->
                    Log.e(TAG, "Error al obtener películas", exception)
                    _state.value = UiState.Error(exception.message ?: "Error desconocido")
                }
            )
        }
    }

    fun toggleLike(movieTitle: String) {
        Log.d(TAG, "Toggle like para: $movieTitle")
        viewModelScope.launch {
            try {
                toggleLikeUseCase(movieTitle)
                Log.d(TAG, "Like toggled exitosamente para: $movieTitle")
            } catch (e: Exception) {
                Log.e(TAG, "Error al hacer toggle like", e)
            }
        }
    }
}