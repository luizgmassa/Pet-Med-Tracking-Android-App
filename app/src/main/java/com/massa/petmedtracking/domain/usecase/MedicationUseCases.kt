package com.massa.petmedtracking.domain.usecase

import com.massa.petmedtracking.domain.model.Medication
import com.massa.petmedtracking.domain.repository.PetMedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMedicationsForPetUseCase @Inject constructor(
    private val repository: PetMedRepository
) {
    operator fun invoke(petId: Long): Flow<List<Medication>> {
        return repository.getMedicationsForPet(petId)
    }
}

class AddMedicationUseCase @Inject constructor(
    private val repository: PetMedRepository
) {
    suspend operator fun invoke(medication: Medication) {
        repository.createMedication(medication)
    }
}
