package org.utl.ui.theme.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.utl.ui.theme.componentes.PlatiloCard
import org.utl.viewmodel.MenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    viewModel: MenuViewModel = viewModel(),
    onVerPedidoClick: () -> Unit = {} // Nuevo evento para navegar
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Menú del Día") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            // Solo mostramos el botón si hay cosas en el pedido (dinero > 0)
            if (state.total > 0) {
                ExtendedFloatingActionButton(
                    onClick = { onVerPedidoClick() },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Carrito"
                        )
                    },
                    text = {
                        // Muestra la cantidad de platillos y el total
                        Text(text = "Ver Pedido (${state.pedidoActual.size}) - $${state.total}")
                    }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(state.platillos) { platilloActual ->
                PlatiloCard(
                    platillo = platilloActual,
                    onAddClick = {
                        viewModel.agregarAlPedido(platilloActual)
                    }
                )
            }
        }
    }
}