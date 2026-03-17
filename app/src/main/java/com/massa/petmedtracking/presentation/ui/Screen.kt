package com.massa.petmedtracking.presentation.ui

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data class PetDetail(val petId: Long) : Screen()
}
