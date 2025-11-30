package org.utl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.utl.db.PlatilloRepository
import org.utl.model.Pedido
import org.utl.model.PedidoDetalle
import org.utl.model.Platillo

class PedidoViewModel (private val repository: PedidoViewModel) : ViewModel(){
    private val _uiState = MutableStateFlow(PedidoUiState())
    val uiState: StateFlow<PedidoUiState> = _uiState.asStateFlow()

    fun selectMesa (mesa: Int){
        _uiState.value = _uiState.value.copy(
            mesa = mesa
        )
    }

    fun agregarPlatillo(platillo: Platillo) {
        _uiState.value = _uiState.value.let { estadoActual ->

            // Buscamos si el platillo ya existe
            val existente = estadoActual.pedidoActual.find { it.idPlatillo == platillo.id }

            val listaActualizada =
                if (existente != null) {
                    // Si ya existe, aumentamos la cantidad de ese mismo platillo
                    estadoActual.pedidoActual.map {
                        if (it.idPlatillo == platillo.id)
                            it.copy(cantidad = it.cantidad + 1)
                        else it
                    }
                } else {
                    // Si no existe, agregamos un nuevo PedidoDetalle
                    estadoActual.pedidoActual + PedidoDetalle(
                        id = 0,
                        idPedido = 0,
                        idPlatillo = platillo.id,
                        nombrePedido = platillo.nombre,
                        precioPedido = platillo.precio,
                        cantidad = 1
                    )
                }

            // Calcular total actualizado
            val nuevoTotal = listaActualizada.sumOf { it.precioPedido * it.cantidad }

            estadoActual.copy(
                pedidoActual = listaActualizada,
                total = nuevoTotal
            )
        }
    }

    //para la base de datos
    suspend fun confirmPedido (){
        val estadoActual = _uiState.value

        //keda pendiente :)
    }

}