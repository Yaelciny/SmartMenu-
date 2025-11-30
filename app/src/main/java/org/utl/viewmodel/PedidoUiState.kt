package org.utl.viewmodel

import org.utl.model.PedidoDetalle

data class PedidoUiState(
    val pedidoActual: List<PedidoDetalle> = emptyList(),
    val total: Double = 0.0,
    val mesa: Int = 0
)