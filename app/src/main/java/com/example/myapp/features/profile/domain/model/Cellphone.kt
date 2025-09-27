package com.example.myapp.features.profile.domain.model

@JvmInline
value class Cellphone(val value: String) {
    init {
        require(value.isNotEmpty()) { "Cellphone must not be empty" }
        require(value.matches(Regex("^[0-9]{8,15}\$"))) {
            "Cellphone must contain 8-15 digits only"
        }
    }

    override fun toString(): String = value
}
