package com.massa.petmedtracking.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "pets")
data class PetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val species: String,
    val breed: String,
    val dateOfBirth: Date?,
    val ownerName: String,
    val ownerContact: String,
    val createdAt: Date,
    val updatedAt: Date
)
