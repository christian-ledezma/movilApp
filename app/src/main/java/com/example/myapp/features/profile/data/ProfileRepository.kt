package com.example.myapp.features.profile.data

import com.example.myapp.features.profile.domain.model.Cellphone
import com.example.myapp.features.profile.domain.model.Email
import com.example.myapp.features.profile.domain.model.ProfileModel
import com.example.myapp.features.profile.domain.model.ProfileName
import com.example.myapp.features.profile.domain.model.Summary
import com.example.myapp.features.profile.domain.model.UrlPath
import com.example.myapp.features.profile.domain.repository.IProfileRepository

class ProfileRepository: IProfileRepository {
    override fun fetchData(): Result<ProfileModel> {
        return try {
            Result.success(
                ProfileModel(
                    name = ProfileName("Homero J. Simpson"),
                    email = Email("homero.simpson@springfieldmail.com"),
                    cellphone = Cellphone("9395557422"),
                    pathUrl = UrlPath("https://www.viaempresa.cat/uploads/s1/43/99/69/homer.jpg"),
                    summary = Summary("Ciudadano de Springfield y dedicado inspector de seguridad en la Planta Nuclear.")
                )
            )
        } catch (e: IllegalArgumentException) {
            Result.failure(e)
        }
    }
}