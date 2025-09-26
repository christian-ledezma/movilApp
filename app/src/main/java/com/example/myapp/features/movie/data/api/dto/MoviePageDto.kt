package com.example.myapp.features.movie.data.api.dto

data class MoviePageDto(
    val page: Int,
    val results: List<MovieDto>
)
