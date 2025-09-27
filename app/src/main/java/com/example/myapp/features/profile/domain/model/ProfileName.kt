package com.example.myapp.features.profile.domain.model

@JvmInline
value class ProfileName(val value: String) {
    init {
        require(value.isNotEmpty()) { "Name must not be empty" }
        require(value.length <= 50) { "Name must be at most 50 characters" }
    }

    override fun toString(): String = value
}