package com.example.myapp.data.Repository

import com.example.myapp.domain.model.UserModel
import com.example.myapp.domain.repository.IGitgubRepository

class GithubRepository: IGitgubRepository {
    override fun findbyNick(value: String): Result<UserModel> {
        return Result.success(UserModel(nickname = "calyr", pathUrl = "https://avatars.githubusercontent.com/u/874321?v=4"))
    }
}