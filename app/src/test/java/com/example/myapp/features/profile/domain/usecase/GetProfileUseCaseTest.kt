package com.example.myapp.features.profile.domain.usecase

import com.example.myapp.features.profile.domain.model.ProfileModel
import com.example.myapp.features.profile.domain.model.ProfileName
import com.example.myapp.features.profile.domain.model.Email
import com.example.myapp.features.profile.domain.model.Cellphone
import com.example.myapp.features.profile.domain.model.UrlPath
import com.example.myapp.features.profile.domain.model.Summary
import com.example.myapp.features.profile.domain.repository.IProfileRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetProfileUseCaseTest {

    private val repository = mockk<IProfileRepository>()
    private val useCase = GetProfileUseCase(repository)

    @Test
    fun `should return success when repository returns profile`() = runTest {
        // Arrange
        val expected = ProfileModel(
            name = ProfileName("Homero J. Simpson"),
            email = Email("homero.simpson@springfieldmail.com"),
            cellphone = Cellphone("9395557422"),
            pathUrl = UrlPath("https://www.viaempresa.cat/uploads/s1/43/99/69/homer.jpg"),
            summary = Summary("Ciudadano de Springfield y dedicado inspector de seguridad en la Planta Nuclear.")
        )

        coEvery { repository.fetchData() } returns Result.success(expected)

        // Act
        val result = useCase.invoke()

        // Assert
        assert(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `should return failure when repository returns error`() = runTest {
        // Arrange
        val exception = Exception("Profile not found")
        coEvery { repository.fetchData() } returns Result.failure(exception)

        // Act
        val result = useCase.invoke()

        // Assert
        assert(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
