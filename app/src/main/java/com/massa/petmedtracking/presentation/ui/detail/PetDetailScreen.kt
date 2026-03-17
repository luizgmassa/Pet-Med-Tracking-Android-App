package com.massa.petmedtracking.presentation.ui.detail

import java.util.Date
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import android.content.Intent
import com.massa.petmedtracking.domain.model.Medication
import com.massa.petmedtracking.presentation.PetMedViewModel
import com.massa.petmedtracking.presentation.ui.components.AddMedicationDialog
import com.massa.petmedtracking.util.PdfGenerator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetDetailScreen(
    petId: Long,
    onNavigateBack: () -> Unit,
    viewModel: PetMedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddMedicationDialog by remember { mutableStateOf(false) }
    
    val pet = uiState.pets.find { it.id == petId }

    LaunchedEffect(petId, pet) {
        if (pet != null) {
            viewModel.selectPet(pet)
        }
    }

    if (showAddMedicationDialog && pet != null) {
        AddMedicationDialog(
            onDismissRequest = { showAddMedicationDialog = false },
            onSaveMedication = { name, dosage, frequency, notes, startDate, durationDays ->
                viewModel.addMedication(
                    Medication(
                        id = 0,
                        petId = pet.id,
                        name = name,
                        dosage = dosage,
                        frequency = frequency,
                        notes = notes,
                        startDate = startDate,
                        durationDays = durationDays
                    )
                )
                showAddMedicationDialog = false
            }
        )
    }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pet?.name ?: "Pet Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (pet != null && uiState.medications.isNotEmpty()) {
                        IconButton(onClick = {
                            val pdfGenerator = PdfGenerator(context)
                            val file = pdfGenerator.generateMedicationHandout(pet, uiState.medications)
                            if (file != null) {
                                val uri = FileProvider.getUriForFile(
                                    context,
                                    "${context.packageName}.fileprovider",
                                    file
                                )
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "application/pdf"
                                    putExtra(Intent.EXTRA_STREAM, uri)
                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                }
                                context.startActivity(Intent.createChooser(intent, "Share Handout"))
                            }
                        }) {
                            Icon(Icons.Default.Share, contentDescription = "Share PDF")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddMedicationDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Medication")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading && uiState.medications.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.medications.isEmpty()) {
                Text(
                    text = "No medications assigned.",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.medications) { medication ->
                        MedicationCard(medication = medication)
                    }
                }
            }
        }
    }
}

@Composable
fun MedicationCard(medication: Medication) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = medication.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Dosage: ${medication.dosage}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Frequency: ${medication.frequency.name}", style = MaterialTheme.typography.bodyMedium)
            if (medication.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Notes: ${medication.notes}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
