package com.massa.petmedtracking.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.massa.petmedtracking.data.local.converter.Converters
import com.massa.petmedtracking.data.local.dao.MedicationDao
import com.massa.petmedtracking.data.local.dao.PetDao
import com.massa.petmedtracking.data.local.entity.MedicationEntity
import com.massa.petmedtracking.data.local.entity.PetEntity

@Database(
    entities = [PetEntity::class, MedicationEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class PetMedDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun medicationDao(): MedicationDao
}
