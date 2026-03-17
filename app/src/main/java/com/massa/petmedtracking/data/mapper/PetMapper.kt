package com.massa.petmedtracking.data.mapper

import com.massa.petmedtracking.data.local.entity.PetEntity
import com.massa.petmedtracking.data.remote.dto.PetDto
import com.massa.petmedtracking.domain.model.Pet
import java.util.Date

fun PetEntity.toDomain(): Pet {
    return Pet(
        id = id,
        name = name,
        species = species,
        breed = breed,
        dateOfBirth = dateOfBirth,
        ownerName = ownerName,
        ownerContact = ownerContact
    )
}

fun Pet.toDto(): PetDto {
    return PetDto(
        id = if (id == 0L) null else id,
        name = name,
        species = species,
        breed = breed,
        dateOfBirth = dateOfBirth?.time,
        ownerName = ownerName,
        ownerContact = ownerContact,
        createdAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis()
    )
}

fun PetDto.toEntity(): PetEntity {
    return PetEntity(
        id = id ?: 0L,
        name = name,
        species = species,
        breed = breed,
        dateOfBirth = dateOfBirth?.let { Date(it) },
        ownerName = ownerName,
        ownerContact = ownerContact,
        createdAt = Date(createdAt),
        updatedAt = Date(updatedAt)
    )
}
