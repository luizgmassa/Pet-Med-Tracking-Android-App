package com.massa.petmedtracking.presentation

import com.massa.petmedtracking.domain.model.Medication
import com.massa.petmedtracking.domain.model.Pet

data class PetMedUiState(
    val pets: List<Pet> = emptyList(),
    val medications: List<Medication> = emptyList(),
    val selectedPet: Pet? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)