package com.massa.petmedtracking.data.remote.api

import com.massa.petmedtracking.data.remote.dto.FrequencyDto
import com.massa.petmedtracking.data.remote.dto.MedicationDto
import com.massa.petmedtracking.data.remote.dto.PetDto
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PetMedApiService @Inject constructor() {

    private val now = System.currentTimeMillis()

    private val pets = mutableListOf<PetDto>()

    private val medications = mutableListOf<MedicationDto>()

    suspend fun createPet(pet: PetDto): PetDto {
        val newPet = pet.copy(id = (pets.size + 1).toLong(), createdAt = now, updatedAt = now)
        pets.add(newPet)
        return newPet
    }

    suspend fun createMedication(medication: MedicationDto): MedicationDto {
        val newMed = medication.copy(id = (medications.size + 1).toLong(), createdAt = now, updatedAt = now)
        medications.add(newMed)
        return newMed
    }
}
