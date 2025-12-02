package org.utl.ui.theme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.utl.viewmodel.MenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenScreen(
    viewModel: MenuViewModel,
    onBackClick: () -> Unit,
    onConfirmarClick: () -> Unit
){
    val state by viewModel.uiState.collectAsState()
    val clientesDisponibles by viewModel.listaCliente.collectAsState()
    val clienteActual by viewModel.clienteSeleccionado.collectAsState()

    // variable para mostrar la lista
    var mostrarLista by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Resumen") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total: $${state.total}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Button(onClick = onConfirmarClick) {
                        Icon(Icons.Default.Check, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Enviar")
                    }
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // TARJETA PARA ELEGIR CLIENTE
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                onClick = { mostrarLista = true } // Al dar clic a la tarjeta, se abre la lista
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Person, contentDescription = null)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Cliente:", style = MaterialTheme.typography.labelSmall)
                        Text(
                            text = clienteActual?.nombre ?: "Público General (Clic para cambiar)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Text("Tus platillos:", style = MaterialTheme.typography.titleMedium)

            // LISTA DE PLATILLOS
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(state.pedidoActual) { detalle ->
                    ListItem(
                        headlineContent = { Text(detalle.nombre) },
                        trailingContent = { Text("$${detalle.precio}", fontWeight = FontWeight.Bold) },
                    )
                    Divider()
                }
            }
        }

        // EL DIÁLOGO DE SELECCION
        if (mostrarLista) {
            AlertDialog(
                onDismissRequest = { mostrarLista = false },
                title = { Text("Elige un cliente") },
                text = {
                    LazyColumn(modifier = Modifier.height(300.dp)) {
                        // Opción 1: Nadie (Público General)
                        item {
                            ListItem(
                                headlineContent = { Text("Público General") },
                                modifier = Modifier.clickable {
                                    viewModel.seleccionarCliente(null)
                                    mostrarLista = false
                                }
                            )
                        }
                        // Lista de tu base de datos
                        items(clientesDisponibles) { cliente ->
                            ListItem(
                                headlineContent = { Text(cliente.nombre) },
                                modifier = Modifier.clickable {
                                    viewModel.seleccionarCliente(cliente)
                                    mostrarLista = false
                                }
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { mostrarLista = false }) { Text("Cerrar") }
                }
            )
        }
    }
}