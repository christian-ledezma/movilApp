package com.example.myapp.features.profile.domain.model

@JvmInline
value class Email(val value: String) {
    init {
        require(value.isNotEmpty()) { "Email must not be empty" }
        require(android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            "Invalid email format"
        }
    }

    override fun toString(): String = value
}