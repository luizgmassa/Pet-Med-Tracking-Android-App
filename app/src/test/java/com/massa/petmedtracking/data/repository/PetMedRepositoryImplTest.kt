package com.massa.petmedtracking.data.repository

import app.cash.turbine.test
import com.massa.petmedtracking.data.local.dao.MedicationDao
import com.massa.petmedtracking.data.local.dao.PetDao
import com.massa.petmedtracking.data.local.entity.PetEntity
import com.massa.petmedtracking.data.remote.api.PetMedApiService
import com.massa.petmedtracking.data.remote.dto.PetDto
import com.massa.petmedtracking.domain.model.Pet
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date

class PetMedRepositoryImplTest {

    private lateinit var repository: PetMedRepositoryImpl
    private val petDao: PetDao = mockk()
    private val medicationDao: MedicationDao = mockk()
    private val apiService: PetMedApiService = mockk()

    @Before
    fun setUp() {
        repository = PetMedRepositoryImpl(petDao, medicationDao, apiService)
    }

    @Test
    fun `getAllPets returns mapped domain objects`() = runTest {
        val now = Date()
        val petEntities = listOf(
            PetEntity(1, "Max", "Dog", "Golden", now, "John", "123", now, now)
        )
        every { petDao.getAllPets() } returns flowOf(petEntities)

        repository.getAllPets().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("Max", result[0].name)
            awaitComplete()
        }
    }

    @Test
    fun `createPet calls api and then dao`() = runTest {
        val now = Date()
        val pet = Pet(id = 0, name = "Max", species = "Dog", breed = "Golden", dateOfBirth = now, ownerName = "John", ownerContact = "123")
        val petDto = PetDto(1, "Max", "Dog", "Golden", now.time, "John", "123", now.time, now.time)
        
        coEvery { apiService.createPet(any()) } returns petDto
        coEvery { petDao.insertPet(any()) } returns 1L

        repository.createPet(pet)

        coVerify { apiService.createPet(any()) }
        coVerify { petDao.insertPet(any()) }
    }
}
