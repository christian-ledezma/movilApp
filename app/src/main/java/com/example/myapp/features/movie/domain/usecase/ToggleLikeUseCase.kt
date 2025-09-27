package com.example.myapp.features.movie.domain.usecase

import com.example.myapp.features.movie.data.repository.LikedMovieRepository

class ToggleLikeUseCase(
    private val likedMovieRepository: LikedMovieRepository
) {
    suspend operator fun invoke(movieTitle: String) {
        likedMovieRepository.toggleLike(movieTitle)
    }
}