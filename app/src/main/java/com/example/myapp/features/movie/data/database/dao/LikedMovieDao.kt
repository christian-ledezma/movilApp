package com.example.myapp.features.movie.data.local.dao

import androidx.room.*
import com.example.myapp.features.movie.data.database.entity.LikedMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LikedMovieDao {

    @Query("SELECT * FROM liked_movies ORDER BY likedAt DESC")
    fun getAllLikedMovies(): Flow<List<LikedMovieEntity>>

    @Query("SELECT movieTitle FROM liked_movies")
    fun getLikedMovieTitles(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikedMovie(movie: LikedMovieEntity)

    @Query("DELETE FROM liked_movies WHERE movieTitle = :movieTitle")
    suspend fun deleteLikedMovie(movieTitle: String)

    @Query("SELECT EXISTS(SELECT 1 FROM liked_movies WHERE movieTitle = :movieTitle)")
    suspend fun isMovieLiked(movieTitle: String): Boolean
}
