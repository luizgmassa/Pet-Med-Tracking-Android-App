package com.massa.petmedtracking.data.remote.dto

data class MedicationDto(
    val id: Long? = null,
    val petId: Long,
    val name: String,
    val dosage: String,
    val frequency: FrequencyDto,
    val notes: String,
    val startDate: Long,
    val durationDays: Int?,
    val createdAt: Long,
    val updatedAt: Long
)
