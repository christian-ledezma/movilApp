package com.example.myapp.features.movie.data.repository

import com.example.myapp.features.movie.data.datasource.MovieRemoteDataSource
import com.example.myapp.features.movie.domain.model.MovieModel
import com.example.myapp.features.movie.domain.repository.IMovieRepository

class MovieRepository(
    private val movieRemoteDataSource: MovieRemoteDataSource
) : IMovieRepository {
    override suspend fun fetchPopularMovies(): Result<List<MovieModel>>
            = movieRemoteDataSource.fetchPopularMovies()
}