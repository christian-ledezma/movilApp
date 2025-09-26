package com.example.myapp.features.github.data.api.dto

import com.example.myapp.features.github.domain.model.UrlPath
import com.google.gson.annotations.SerializedName

data class GithubDto(
    val login: String,
    @SerializedName("avatar_url") val url: UrlPath,
    @SerializedName("name") val name: String
)

