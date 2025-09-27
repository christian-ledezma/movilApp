package com.example.myapp.features.profile.domain.model

import org.junit.Assert.assertThrows
import org.junit.Test

class ProfileNameTest {

    @Test
    fun `should throw exception if name is empty`() {
        assertThrows(IllegalArgumentException::class.java) {
            ProfileName("")
        }
    }

    @Test
    fun `should throw exception if name exceeds 50 characters`() {
        val longName = "a".repeat(51)
        assertThrows(IllegalArgumentException::class.java) {
            ProfileName(longName)
        }
    }

    @Test
    fun `should create ProfileName when valid`() {
        val name = ProfileName("Homero J. Simpson")
        assert(name.value == "Homero J. Simpson")
    }
}
