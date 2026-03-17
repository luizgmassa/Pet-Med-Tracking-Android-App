package com.massa.petmedtracking.data.mapper

import com.massa.petmedtracking.data.local.entity.MedicationEntity
import com.massa.petmedtracking.data.remote.dto.MedicationDto
import com.massa.petmedtracking.data.local.entity.Frequency as EntityFrequency
import com.massa.petmedtracking.data.remote.dto.FrequencyDto as DtoFrequency
import com.massa.petmedtracking.domain.model.Frequency as DomainFrequency
import com.massa.petmedtracking.domain.model.Medication
import java.util.Date

fun MedicationEntity.toDomain(): Medication {
    return Medication(
        id = id,
        petId = petId,
        name = name,
        dosage = dosage,
        frequency = DomainFrequency.valueOf(frequency.name),
        notes = notes,
        startDate = startDate,
        durationDays = durationDays
    )
}

fun Medication.toDto(): MedicationDto {
    return MedicationDto(
        id = if (id == 0L) null else id,
        petId = petId,
        name = name,
        dosage = dosage,
        frequency = DtoFrequency.valueOf(frequency.name),
        notes = notes,
        startDate = startDate.time,
        durationDays = durationDays,
        createdAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis()
    )
}

fun MedicationDto.toEntity(): MedicationEntity {
    return MedicationEntity(
        id = id ?: 0L,
        petId = petId,
        name = name,
        dosage = dosage,
        frequency = EntityFrequency.valueOf(frequency.name),
        notes = notes,
        startDate = Date(startDate),
        durationDays = durationDays,
        createdAt = Date(createdAt),
        updatedAt = Date(updatedAt)
    )
}
