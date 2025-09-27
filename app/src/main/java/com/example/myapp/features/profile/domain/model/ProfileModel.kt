package com.example.myapp.features.profile.domain.model

data class ProfileModel (
    val pathUrl: UrlPath,
    val name: ProfileName,
    val email: Email,
    val cellphone: Cellphone,
    val summary: Summary
)