package com.massa.petmedtracking.di

import android.content.Context
import androidx.room.Room
import com.massa.petmedtracking.data.local.PetMedDatabase
import com.massa.petmedtracking.data.local.dao.MedicationDao
import com.massa.petmedtracking.data.local.dao.PetDao
import com.massa.petmedtracking.data.remote.api.PetMedApiService
import com.massa.petmedtracking.data.repository.PetMedRepositoryImpl
import com.massa.petmedtracking.domain.repository.PetMedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePetMedDatabase(@ApplicationContext context: Context): PetMedDatabase {
        return Room.databaseBuilder(
            context,
            PetMedDatabase::class.java,
            "pet_med_database"
        ).build()
    }

    @Provides
    fun providePetDao(database: PetMedDatabase): PetDao {
        return database.petDao()
    }

    @Provides
    fun provideMedicationDao(database: PetMedDatabase): MedicationDao {
        return database.medicationDao()
    }

    @Provides
    @Singleton
    fun providePetMedApiService(): PetMedApiService {
        return PetMedApiService()
    }

    @Provides
    @Singleton
    fun providePetMedRepository(
        petDao: PetDao,
        medicationDao: MedicationDao,
        apiService: PetMedApiService
    ): PetMedRepository {
        return PetMedRepositoryImpl(
            petDao = petDao,
            medicationDao = medicationDao,
            apiService = apiService
        )
    }
}
