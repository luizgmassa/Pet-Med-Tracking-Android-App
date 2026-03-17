package com.massa.petmedtracking.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.massa.petmedtracking.data.local.entity.MedicationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: MedicationEntity): Long

    @Query("SELECT * FROM medications WHERE id = :id")
    fun getMedicationById(id: Long): Flow<MedicationEntity?>

    @Query("SELECT * FROM medications WHERE petId = :petId ORDER BY startDate DESC")
    fun getMedicationsForPet(petId: Long): Flow<List<MedicationEntity>>

    @Query("SELECT * FROM medications ORDER BY startDate DESC")
    fun getAllMedications(): Flow<List<MedicationEntity>>
}
