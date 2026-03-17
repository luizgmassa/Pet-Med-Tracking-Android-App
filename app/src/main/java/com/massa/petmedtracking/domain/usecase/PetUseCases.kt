package com.massa.petmedtracking.domain.usecase

import com.massa.petmedtracking.domain.model.Pet
import com.massa.petmedtracking.domain.repository.PetMedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPetsUseCase @Inject constructor(
    private val repository: PetMedRepository
) {
    operator fun invoke(): Flow<List<Pet>> {
        return repository.getAllPets()
    }
}

class AddPetUseCase @Inject constructor(
    private val repository: PetMedRepository
) {
    suspend operator fun invoke(pet: Pet) {
        repository.createPet(pet)
    }
}
