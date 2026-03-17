package com.massa.petmedtracking.domain.usecase

import com.massa.petmedtracking.domain.model.Pet
import com.massa.petmedtracking.domain.repository.PetMedRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class PetUseCasesTest {

    private val repository: PetMedRepository = mockk()

    @Test
    fun `GetPetsUseCase calls repository`() = runTest {
        val useCase = GetPetsUseCase(repository)
        val pets = listOf(Pet(1, "Max", "Dog", "Golden", Date(), "John", "123"))
        every { repository.getAllPets() } returns flowOf(pets)

        useCase().collect { result ->
            assertEquals(pets, result)
        }
    }

    @Test
    fun `AddPetUseCase calls repository`() = runTest {
        val useCase = AddPetUseCase(repository)
        val pet = Pet(0, "Max", "Dog", "Golden", Date(), "John", "123")
        coEvery { repository.createPet(pet) } returns Unit

        useCase(pet)

        coVerify { repository.createPet(pet) }
    }
}
