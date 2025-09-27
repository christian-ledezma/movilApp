package com.example.myapp.features.profile.domain.model

@JvmInline
value class Email(val value: String) {
    init {
        require(value.isNotEmpty()) { "Email must not be empty" }
        // Regex simple para validar email en JVM y Android
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        require(emailRegex.matches(value)) { "Invalid email format" }
    }

    override fun toString(): String = value
}