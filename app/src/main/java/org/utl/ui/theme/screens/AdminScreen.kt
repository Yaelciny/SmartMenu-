package org.utl.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.utl.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    viewModel: AdminViewModel,
    onLogout: () -> Unit
){
    val platillos by viewModel.listaPlatillo.collectAsState()
    var mostrarDialogo by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Gestion de Menu") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                ),
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Cerrar Sesion",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {mostrarDialogo = true}) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Platillo")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(platillos) {platillo ->
                Card(elevation = CardDefaults.cardElevation(2.dp)) {
                    Row(
                        modifier = Modifier.padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column{
                            Text(platillo.nombre,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            "$${platillo.precio}",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
        if (mostrarDialogo) {
            DialogoNuevoPlatillo(
                onDismiss = { mostrarDialogo = false },
                onConfirm = { nombre, precio ->
                    viewModel.agregarPlatillo(nombre, precio)
                    mostrarDialogo = false
                }
            )
        }
    }
}

@Composable
fun DialogoNuevoPlatillo(onDismiss: () -> Unit,
                         onConfirm: (String, Double) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var precioStr by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text("Nuevo Platillo")},
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del Platillo") }
                )
                OutlinedTextField(
                    value = precioStr,
                    onValueChange = { precioStr = it },
                    label = { Text("Precio ($)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val precio = precioStr.toDoubleOrNull()?:0.0
                if (nombre.isNotEmpty() && precio > 0) {
                    onConfirm(nombre, precio)
                }
            }) { Text("Guardar")}
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar")}
        }
    )
}