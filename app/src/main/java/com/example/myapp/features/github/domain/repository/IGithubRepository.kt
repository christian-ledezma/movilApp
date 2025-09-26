package com.example.myapp.features.github.domain.repository

import com.example.myapp.features.github.domain.model.UserModel

interface IGithubRepository {
    suspend fun findByNick(value: String): Result<UserModel>
}