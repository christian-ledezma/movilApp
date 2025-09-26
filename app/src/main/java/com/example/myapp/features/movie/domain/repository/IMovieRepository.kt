package com.example.myapp.features.movie.domain.repository

import com.example.myapp.features.movie.domain.model.MovieModel

interface IMovieRepository {
    suspend fun fetchPopularMovies(): Result<List<MovieModel>>
}