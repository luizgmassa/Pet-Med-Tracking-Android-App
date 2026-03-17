package com.massa.petmedtracking.data.repository

import com.massa.petmedtracking.data.local.dao.MedicationDao
import com.massa.petmedtracking.data.local.dao.PetDao
import com.massa.petmedtracking.data.mapper.toDomain
import com.massa.petmedtracking.data.mapper.toDto
import com.massa.petmedtracking.data.mapper.toEntity
import com.massa.petmedtracking.data.remote.api.PetMedApiService
import com.massa.petmedtracking.domain.model.Medication
import com.massa.petmedtracking.domain.model.Pet
import com.massa.petmedtracking.domain.repository.PetMedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PetMedRepositoryImpl(
    private val petDao: PetDao,
    private val medicationDao: MedicationDao,
    private val apiService: PetMedApiService
) : PetMedRepository {

    override fun getAllPets(): Flow<List<Pet>> {
        return petDao.getAllPets().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getPetById(id: Long): Flow<Pet?> {
        return petDao.getPetById(id).map { it?.toDomain() }
    }

    override suspend fun createPet(pet: Pet) {
        val createdDto = apiService.createPet(pet.toDto())
        petDao.insertPet(createdDto.toEntity())
    }

    override fun getMedicationsForPet(petId: Long): Flow<List<Medication>> {
        return medicationDao.getMedicationsForPet(petId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getMedicationById(id: Long): Flow<Medication?> {
        return medicationDao.getMedicationById(id).map { it?.toDomain() }
    }

    override suspend fun createMedication(medication: Medication) {
        val createdDto = apiService.createMedication(medication.toDto())
        medicationDao.insertMedication(createdDto.toEntity())
    }
}
