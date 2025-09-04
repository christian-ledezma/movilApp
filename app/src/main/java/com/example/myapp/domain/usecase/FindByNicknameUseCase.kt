package com.example.myapp.domain.usecase

import com.example.myapp.domain.model.UserModel
import com.example.myapp.domain.repository.IGitgubRepository

class FindByNicknameUseCase(
    val repository : IGitgubRepository
) {
    fun invoke(nickname : String) : Result<UserModel> {
        return repository.findbyNick(value = nickname)
        }
}