package com.massa.petmedtracking.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddPetDialog(
    onDismissRequest: () -> Unit,
    onSavePet: (name: String, species: String, breed: String, ownerName: String, ownerContact: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var species by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var ownerContact by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Add New Pet") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                OutlinedTextField(value = species, onValueChange = { species = it }, label = { Text("Species") })
                OutlinedTextField(value = breed, onValueChange = { breed = it }, label = { Text("Breed") })
                OutlinedTextField(value = ownerName, onValueChange = { ownerName = it }, label = { Text("Owner Name") })
                OutlinedTextField(value = ownerContact, onValueChange = { ownerContact = it }, label = { Text("Owner Contact") })
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isNotBlank()) {
                    onSavePet(name, species, breed, ownerName, ownerContact)
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}
