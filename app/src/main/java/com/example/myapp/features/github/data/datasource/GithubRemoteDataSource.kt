package com.example.myapp.features.github.data.datasource

import android.util.Log
import com.example.myapp.features.github.data.api.GithubService
import com.example.myapp.features.github.data.error.DataException
import com.example.myapp.features.github.domain.model.UrlPath
import com.example.myapp.features.github.domain.model.UserModel

class GithubRemoteDataSource (
    val githubService: GithubService
){
    suspend fun getUser(nick: String): Result<UserModel> {
        Log.d("GithubRemoteDataSource", "Buscando usuario: $nick")//
        val response = githubService.getInfoAvatar(nick)
        if(response.isSuccessful) {
            val body = response.body()
            Log.d("GithubRemoteDataSource", "Body recibido: $body")//
            if (body != null) {
                try {
                    return Result.success(UserModel(
                        body.login,
                        UrlPath(body.url.toString()),
                        body.name
                    ))
                    Log.d("GithubRemoteDataSource", "Usuario creado exitosamente:")
                } catch (e: Exception) {
                    Log.e("GithubRemoteDataSource", "Error creando UserModel: ${e.message}", e)//
                    return Result.failure(
                        DataException.Unknown(
                            e.message.toString()))
                }

            } else {
                Log.e("GithubRemoteDataSource", "Body es null")//
                return Result.failure(DataException.NoContent)
            }
        } else if (response.code() == 404) {
            Log.e("GithubRemoteDataSource", "Usuario no encontrado: 404")
            return Result.failure(DataException.HttpNotFound)
        } else {
            Log.e("GithubRemoteDataSource", "Error HTTP: ${response.code()} - ${response.message()}")
            return Result.failure(DataException.Unknown(response.message()))
        }
    }
}