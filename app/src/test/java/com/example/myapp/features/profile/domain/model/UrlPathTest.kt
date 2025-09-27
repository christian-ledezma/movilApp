package com.example.myapp.features.profile.domain.model

import org.junit.Assert.assertThrows
import org.junit.Test

class UrlPathTest {
    @Test(expected = Exception::class)
    fun `test UrlPath`() {
        UrlPath("myurl")
        UrlPath("")
    }


    @Test
    fun `should throw exception if path does not start with slash`() {
        assertThrows(IllegalArgumentException::class.java) {
            UrlPath("myurl")
        }
    }

    @Test
    fun `should throw exception if path is empty`() {
        assertThrows(IllegalArgumentException::class.java) {
            UrlPath("")
        }
    }

    @Test
    fun `should create UrlPath when value is valid`() {
        val url = UrlPath("https://github.com/users/juan")
        assert(url.value == "https://github.com/users/juan")
    }
}