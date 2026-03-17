package com.massa.petmedtracking.presentation.ui.home

import java.util.Date
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.massa.petmedtracking.domain.model.Pet
import com.massa.petmedtracking.presentation.PetMedViewModel
import com.massa.petmedtracking.presentation.ui.components.AddPetDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToPetDetails: (Long) -> Unit,
    viewModel: PetMedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddPetDialog by remember { mutableStateOf(false) }

    if (showAddPetDialog) {
        AddPetDialog(
            onDismissRequest = { showAddPetDialog = false },
            onSavePet = { name, species, breed, ownerName, ownerContact ->
                viewModel.addPet(
                    Pet(
                        id = 0,
                        name = name,
                        species = species,
                        breed = breed,
                        dateOfBirth = Date(),
                        ownerName = ownerName,
                        ownerContact = ownerContact
                    )
                )
                showAddPetDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pet Med Tracker") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddPetDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Pet")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading && uiState.pets.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.pets.isEmpty()) {
                Text(
                    text = "No pets tracked yet. Add one!",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.pets) { pet ->
                        PetCard(
                            pet = pet,
                            onClick = { onNavigateToPetDetails(pet.id) }
                        )
                    }
                }
            }
            
            uiState.error?.let { errorMsg ->
                Text(
                    text = errorMsg,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun PetCard(pet: Pet, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = pet.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "${pet.species} - ${pet.breed}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

