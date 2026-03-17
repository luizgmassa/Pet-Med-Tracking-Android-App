package com.massa.petmedtracking.data.remote.api

import com.massa.petmedtracking.data.remote.dto.MedicationDto
import com.massa.petmedtracking.data.remote.dto.PetDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PetMedApiService {

    @GET("pets")
    suspend fun getAllPets(): List<PetDto>

    @GET("pets/{id}")
    suspend fun getPetById(@Path("id") id: Long): PetDto

    @POST("pets")
    suspend fun createPet(@Body pet: PetDto): PetDto

    @GET("pets/{petId}/medications")
    suspend fun getMedicationsForPet(@Path("petId") petId: Long): List<MedicationDto>

    @GET("medications/{id}")
    suspend fun getMedicationById(@Path("id") id: Long): MedicationDto

    @POST("medications")
    suspend fun createMedication(@Body medication: MedicationDto): MedicationDto
}
