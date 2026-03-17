package com.massa.petmedtracking.domain.usecase

import com.massa.petmedtracking.domain.model.Medication
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

class MedicationUseCasesTest {

    private val repository: PetMedRepository = mockk()

    @Test
    fun `GetMedicationsForPetUseCase calls repository`() = runTest {
        val useCase = GetMedicationsForPetUseCase(repository)
        val medications = listOf(mockk<Medication>())
        every { repository.getMedicationsForPet(1L) } returns flowOf(medications)

        useCase(1L).collect { result ->
            assertEquals(medications, result)
        }
    }

    @Test
    fun `AddMedicationUseCase calls repository`() = runTest {
        val useCase = AddMedicationUseCase(repository)
        val medication = mockk<Medication>()
        coEvery { repository.createMedication(medication) } returns Unit

        useCase(medication)

        coVerify { repository.createMedication(medication) }
    }
}
