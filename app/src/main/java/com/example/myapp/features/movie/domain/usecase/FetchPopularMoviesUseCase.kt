package com.example.myapp.features.movie.domain.usecase

import com.example.myapp.features.movie.domain.model.MovieModel
import com.example.myapp.features.movie.domain.repository.IMovieRepository

class FetchPopularMoviesUseCase(
    private val movieRepository: IMovieRepository
) {

    suspend fun invoke(): Result<List<MovieModel>>{
        return movieRepository.fetchPopularMovies()
    }
}