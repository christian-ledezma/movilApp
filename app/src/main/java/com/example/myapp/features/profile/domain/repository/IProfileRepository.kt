package com.example.myapp.features.profile.domain.repository

import com.example.myapp.features.profile.domain.model.ProfileModel

interface IProfileRepository {
    fun fetchData(): Result<ProfileModel>
}