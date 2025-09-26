package com.example.myapp.features.github.data.repository

import com.example.myapp.features.github.data.error.DataException
import com.example.myapp.features.github.data.datasource.GithubRemoteDataSource
import com.example.myapp.features.github.domain.model.UserModel
import com.example.myapp.features.github.domain.model.UrlPath
import com.example.myapp.features.github.domain.model.error.Failure
import com.example.myapp.features.github.domain.repository.IGithubRepository

class GithubRepository (
    val remoteDataSource: GithubRemoteDataSource
): IGithubRepository {
    override suspend fun findByNick(value: String): Result<UserModel> {
        if(value.isEmpty()) {
            return Result.failure(Exception("El campo no puede estar vacio"))
        }
        val response = remoteDataSource.getUser(value)

        response.fold(
            onSuccess = {
                return Result.success(it)
            },
            onFailure = { exception ->
                val failure = when (exception) {
                    is DataException.Network -> Failure.NetworkConnection
                    is DataException.HttpNotFound -> Failure.NotFound
                    is DataException.NoContent -> Failure.EmptyBody
                    is DataException.Unknown -> Failure.Unknown(exception)
                    else -> Failure.Unknown(exception)
                }
                return Result.failure(failure)
            }
        )
    }
}


//return Result.success(
//UserModel(
//nickname = "christian-ledezma",
//pathUrl = "https://avatars.githubusercontent.com/u/158978534?v=4",
//userName = "chris.ledezma"
//)
//)