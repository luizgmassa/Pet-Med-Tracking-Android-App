package com.massa.petmedtracking.domain.model

import java.util.Date

enum class Frequency {
    ONCE_DAILY,
    TWICE_DAILY,
    EVERY_8_HOURS,
    AS_NEEDED
}

data class Pet(
    val id: Long,
    val name: String,
    val species: String,
    val breed: String,
    val dateOfBirth: Date?,
    val ownerName: String,
    val ownerContact: String
)

data class Medication(
    val id: Long,
    val petId: Long,
    val name: String,
    val dosage: String,
    val frequency: Frequency,
    val notes: String,
    val startDate: Date,
    val durationDays: Int?
)
