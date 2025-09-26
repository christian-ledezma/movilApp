package com.example.myapp.features.github.domain.usecase

import com.example.myapp.features.github.domain.model.UserModel
import com.example.myapp.features.github.domain.repository.IGithubRepository
import kotlinx.coroutines.delay

class FindByNicknameUseCase(
    val repository : IGithubRepository
) {
    suspend fun invoke(nickname : String) : Result<UserModel> {
        delay(500)
        return repository.findByNick(nickname)
        }
}