package com.example.myapp.features.profile.domain.model

@JvmInline
value class Summary(val value: String) {
    init {
        require(value.length <= 250) { "Summary must be at most 250 characters" }
    }

    override fun toString(): String = value
}