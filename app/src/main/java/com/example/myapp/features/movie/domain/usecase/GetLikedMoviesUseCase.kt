package com.example.myapp.features.movie.domain.usecase

import com.example.myapp.features.movie.data.repository.LikedMovieRepository
import kotlinx.coroutines.flow.Flow

class GetLikedMoviesUseCase(
    private val likedMovieRepository: LikedMovieRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return likedMovieRepository.getLikedMovieTitles()
    }
}