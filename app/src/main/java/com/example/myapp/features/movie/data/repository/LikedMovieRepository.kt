package com.example.myapp.features.movie.data.repository

import com.example.myapp.features.movie.data.database.entity.LikedMovieEntity
import com.example.myapp.features.movie.data.local.dao.LikedMovieDao
import kotlinx.coroutines.flow.Flow

interface LikedMovieRepository {
    fun getLikedMovieTitles(): Flow<List<String>>
    suspend fun toggleLike(movieTitle: String)
    suspend fun isMovieLiked(movieTitle: String): Boolean
}

class LikedMovieRepositoryImpl(
    private val dao: LikedMovieDao
) : LikedMovieRepository {

    override fun getLikedMovieTitles(): Flow<List<String>> {
        return dao.getLikedMovieTitles()
    }

    override suspend fun toggleLike(movieTitle: String) {
        if (dao.isMovieLiked(movieTitle)) {
            dao.deleteLikedMovie(movieTitle)
        } else {
            dao.insertLikedMovie(LikedMovieEntity(movieTitle))
        }
    }

    override suspend fun isMovieLiked(movieTitle: String): Boolean {
        return dao.isMovieLiked(movieTitle)
    }
}