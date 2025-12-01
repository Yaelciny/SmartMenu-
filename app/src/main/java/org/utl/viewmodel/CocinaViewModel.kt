package org.utl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.utl.db.PedidoRepository
import org.utl.model.Pedido
import org.utl.model.PedidoConDetalles

class CocinaViewModel(private val repository: PedidoRepository): ViewModel() {

    // se actualiza automaticamente si entra un pedido nuevo
    val pedidosPendientes: StateFlow<List<PedidoConDetalles>> = repository.pedidosEnCocina
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun despacharPedido(idPedido: Int){
        viewModelScope.launch {
            repository.finalizarPedido(idPedido)
        }
    }
}