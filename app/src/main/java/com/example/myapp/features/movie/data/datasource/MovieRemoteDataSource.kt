package com.example.myapp.features.movie.data.datasource

import android.util.Log
import com.example.myapp.features.movie.data.api.MovieService
import com.example.myapp.features.movie.domain.model.MovieModel

class MovieRemoteDataSource (
    private val movieServie: MovieService,
    private val apiKey: String
){
    companion object {
        private const val TAG = "MovieRemoteDataSource"
    }//
    suspend fun fetchPopularMovies(): Result<List<MovieModel>> {
        Log.d(TAG, "Iniciando llamada a API")//
        Log.d(TAG, "API Key: ${apiKey.take(8)}...")//
        val response = movieServie.fetchPopularMovies(apiKey = apiKey)

        Log.d(TAG, "Respuesta recibida - Código: ${response.code()}")
        Log.d(TAG, "Es exitosa: ${response.isSuccessful}")

        return if (response.isSuccessful) {
            val moviePage = response.body()
            Log.d(TAG, "Body es null: ${moviePage == null}")//
            if (moviePage != null) {
                Log.d(TAG, "Resultados en body: ${moviePage.results?.size ?: "null"}")//

                return Result.success(moviePage.results.map { dto ->  MovieModel("https://image.tmdb.org/t/p/w185"+dto.pathUrl, dto.title) } )
            }
            Result.success(emptyList())
        } else {
            Result.failure(Exception("Error"))
        }
    }
}