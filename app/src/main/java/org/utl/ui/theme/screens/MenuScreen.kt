package org.utl.ui.theme.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.utl.model.Platillo
import org.utl.ui.theme.componentes.PlatiloCard
import org.utl.viewmodel.MenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(viewModel: MenuViewModel = viewModel()) {
    // 1. Datos de prueba para ver diseño sin base de datos
    val listaPrueba = listOf(
        Platillo(1, "Hamburguesa Clásica",120.0 , true),
        Platillo(2, "Tacos de Asada",85.0, true),
        Platillo(3, "Refresco", 25.0, true),
        Platillo(4, "Pastel de Chocolate", 60.0, false)
    )

    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text("Menu - Total : $${state.total}")},
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            // Iteramos sobre la lista
            items(state.platillos) { platilloActual ->
                PlatiloCard(
                    platillo = platilloActual,
                    onAddClick = {
                        // Aquí programaremos la logica de agregar al carrito despues
                        viewModel.agregarAlPedido(platilloActual)
                    }
                )
            }
        }
    }
}