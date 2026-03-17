package com.massa.petmedtracking.data.remote.dto

data class PetDto(
    val id: Long? = null,
    val name: String,
    val species: String,
    val breed: String,
    val dateOfBirth: Long?,
    val ownerName: String,
    val ownerContact: String,
    val createdAt: Long,
    val updatedAt: Long
)
