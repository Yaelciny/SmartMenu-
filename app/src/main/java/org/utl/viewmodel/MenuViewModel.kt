package org.utl.viewmodel

import android.view.Menu
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.utl.model.Platillo

class MenuViewModel : ViewModel(){
    //El estado privado significa que solo el viewmodel lo puede modificar
    private val _uiState = MutableStateFlow(MenuUiState())

    //El estado publico la ui solo se puede leer
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    init {
        cargarPlatillos()
    }

    private fun cargarPlatillos(){
        val listaBase = listOf(
            Platillo(1, "Hamburguesa Clásica", 120.0, true),
            Platillo(2, "Tacos de Asada", 85.0, true),
            Platillo(3, "Refresco", 25.0, true),
            Platillo(4, "Pastel de Chocolate", 60.0, false)
        )

        _uiState.update { estadoActual ->
            estadoActual.copy(platillos = listaBase)
        }
    }

    fun agregarAlPedido(platillo: Platillo){
        _uiState.update { estadoActual ->
            val nuevaLista = estadoActual.pedidoActual + platillo
            val nuevoTotal = estadoActual.total + platillo.precio

            estadoActual.copy(
                pedidoActual = nuevaLista,
                total = nuevoTotal
            )
        }
    }
}