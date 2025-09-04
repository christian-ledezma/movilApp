package com.example.myapp.domain.repository

import com.example.myapp.domain.model.UserModel

interface IGitgubRepository {
    fun findbyNick(value: String): Result<UserModel>
}