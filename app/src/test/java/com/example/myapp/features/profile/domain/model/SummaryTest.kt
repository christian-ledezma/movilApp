package com.example.myapp.features.profile.domain.model

import org.junit.Assert.assertThrows
import org.junit.Test

class SummaryTest {

    @Test
    fun `should throw exception if summary exceeds 250 characters`() {
        val longSummary = "a".repeat(251)
        assertThrows(IllegalArgumentException::class.java) {
            Summary(longSummary)
        }
    }

    @Test
    fun `should create Summary when valid`() {
        val summary = Summary("Inspector de seguridad en la Planta Nuclear de Springfield.")
        assert(summary.value == "Inspector de seguridad en la Planta Nuclear de Springfield.")
    }
}
