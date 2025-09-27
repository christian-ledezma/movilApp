package com.example.myapp.features.profile.domain.model

import org.junit.Assert.assertThrows
import org.junit.Test

class EmailTest {

    @Test
    fun `should throw exception if email is empty`() {
        assertThrows(IllegalArgumentException::class.java) {
            Email("")
        }
    }

    @Test
    fun `should throw exception if email is invalid`() {
        assertThrows(IllegalArgumentException::class.java) {
            Email("invalid_email.com")
        }
    }

    @Test
    fun `should create Email when valid`() {
        val email = Email("homero.simpson@springfieldmail.com")
        assert(email.value == "homero.simpson@springfieldmail.com")
    }
}
