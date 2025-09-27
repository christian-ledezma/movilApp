package com.example.myapp.features.profile.domain.model

import org.junit.Assert.assertThrows
import org.junit.Test

class CellphoneTest {

    @Test
    fun `should throw exception if cellphone is empty`() {
        assertThrows(IllegalArgumentException::class.java) {
            Cellphone("")
        }
    }

    @Test
    fun `should throw exception if cellphone contains invalid characters`() {
        assertThrows(IllegalArgumentException::class.java) {
            Cellphone("123-ABC-456")
        }
    }

    @Test
    fun `should throw exception if cellphone is too short`() {
        assertThrows(IllegalArgumentException::class.java) {
            Cellphone("12345")
        }
    }

    @Test
    fun `should throw exception if cellphone is too long`() {
        assertThrows(IllegalArgumentException::class.java) {
            Cellphone("12345678901234567890")
        }
    }

    @Test
    fun `should create Cellphone when valid`() {
        val cellphone = Cellphone("9395557422")
        assert(cellphone.value == "9395557422")
    }
}
