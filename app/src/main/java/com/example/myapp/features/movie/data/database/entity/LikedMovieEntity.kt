package com.example.myapp.features.movie.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_movies")
data class LikedMovieEntity(
    @PrimaryKey
    val movieTitle: String, // Usando título como ID único
    val likedAt: Long = System.currentTimeMillis()
)