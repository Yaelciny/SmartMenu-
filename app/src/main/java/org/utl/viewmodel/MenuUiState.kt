package org.utl.viewmodel

import org.utl.model.Platillo

data class MenuUiState(
    val platillos : List<Platillo> = emptyList(),
    val pedidoActual: List<Platillo> =emptyList(),
    val total : Double = 0.0,
    val isLoading: Boolean = false,
    val mesaSeleccionada: Int = 1
)