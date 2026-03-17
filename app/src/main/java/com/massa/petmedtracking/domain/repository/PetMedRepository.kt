package com.massa.petmedtracking.domain.repository

import com.massa.petmedtracking.domain.model.Medication
import com.massa.petmedtracking.domain.model.Pet
import kotlinx.coroutines.flow.Flow

interface PetMedRepository {
    fun getAllPets(): Flow<List<Pet>>
    fun getPetById(id: Long): Flow<Pet?>
    suspend fun createPet(pet: Pet)
    
    fun getMedicationsForPet(petId: Long): Flow<List<Medication>>
    fun getMedicationById(id: Long): Flow<Medication?>
    suspend fun createMedication(medication: Medication)
}
