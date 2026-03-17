package com.massa.petmedtracking.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.massa.petmedtracking.domain.model.Medication
import com.massa.petmedtracking.domain.model.Pet
import com.massa.petmedtracking.domain.usecase.AddMedicationUseCase
import com.massa.petmedtracking.domain.usecase.AddPetUseCase
import com.massa.petmedtracking.domain.usecase.GetMedicationsForPetUseCase
import com.massa.petmedtracking.domain.usecase.GetPetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetMedViewModel @Inject constructor(
    private val getPetsUseCase: GetPetsUseCase,
    private val addPetUseCase: AddPetUseCase,
    private val getMedicationsForPetUseCase: GetMedicationsForPetUseCase,
    private val addMedicationUseCase: AddMedicationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PetMedUiState(isLoading = true))
    val uiState: StateFlow<PetMedUiState> = _uiState.asStateFlow()

    private var medicationsJob: Job? = null

    init {
        viewModelScope.launch {
            try {
                getPetsUseCase().collect { pets ->
                    _uiState.value = _uiState.value.copy(
                        pets = pets,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load pets"
                )
            }
        }
    }

    fun selectPet(pet: Pet) {
        _uiState.value = _uiState.value.copy(selectedPet = pet, isLoading = true)
        medicationsJob?.cancel()
        
        medicationsJob = viewModelScope.launch {
            try {
                getMedicationsForPetUseCase(pet.id).collect { medications ->
                    _uiState.value = _uiState.value.copy(
                        medications = medications,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load medications"
                )
            }
        }
    }

    fun addPet(pet: Pet) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                addPetUseCase(pet)
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to add pet"
                )
            }
        }
    }

    fun addMedication(medication: Medication) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                addMedicationUseCase(medication)
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to add medication"
                )
            }
        }
    }
}
