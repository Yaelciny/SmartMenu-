package org.utl.viewmodel

import android.view.Menu
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.utl.db.PedidoRepository
import org.utl.db.PlatilloRepository
import org.utl.model.Pedido
import org.utl.model.PedidoDetalle
import org.utl.model.Platillo

class MenuViewModel(
    private val repository: PlatilloRepository,
    private val pedidoRepository: PedidoRepository
        ) : ViewModel(){
    //El estado privado significa que solo el viewmodel lo puede modificar
    private val _uiState = MutableStateFlow(MenuUiState())
    //El estado publico la ui solo se puede leer
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    init {
        //Inicializamom la observacion de la base de datos
        viewModelScope.launch {
            //recolecatamos los datos
            repository.allPlatillos.collect{ listaReal ->
                // Si la lista esta vacia, insertamos datos de prueba automaticamente
                if (listaReal.isEmpty()) {
                    cargarDatosIniciales()
                } else {
                    // Si ya hay datos, actualizamos la UI
                    _uiState.update { it.copy(platillos = listaReal) }
                }
            }
        }
    }

    private fun cargarDatosIniciales(){
        viewModelScope.launch(Dispatchers.IO) {
            val listaPrueba = listOf(
                Platillo(nombre = "Hamburguesa Clásica", precio = 120.0, disponible = true),
                Platillo(nombre = "Tacos de Asada", precio = 85.0, disponible = true),
                Platillo(nombre = "Refresco", precio = 25.0, disponible = true),
                Platillo(nombre = "Pastel de Chocolate", precio = 60.0, disponible = false)
            )
            repository.insertarVarios(listaPrueba)
        }
    }

    fun agregarAlPedido(platillo: Platillo){
        //esta funcion keda pendiente de cambiar adapatandola
        //a pedido y pedido detalle
        _uiState.update { estadoActual ->
            val nuevaLista = estadoActual.pedidoActual + platillo
            val nuevoTotal = estadoActual.total + platillo.precio

            estadoActual.copy(
                pedidoActual = nuevaLista,
                total = nuevoTotal
            )
        }
    }
    fun seleccionarMesa(numeroMesa: Int){
        _uiState.update { it.copy(mesaSeleccionada = numeroMesa) }
    }

    fun confirmarPedido(){
        viewModelScope.launch {
            val estadoActual = _uiState.value

            if (estadoActual.pedidoActual.isNotEmpty()) {

                //Crear el Encabezado del Pedido
                val nuevoPedido = Pedido(
                    mesa = estadoActual.mesaSeleccionada,
                    estado = "Pendiente"
                )
                val idPedidoGenerado = pedidoRepository.insertPedido(nuevoPedido)

                // Agrupar platillos iguales para sacar la cantidad
                // Convertimos la lista plana (Hamburguesa, Refresco, Hamburguesa)
                // en un mapa: { Hamburguesa=2, Refresco=1 }
                val platillosAgrupados = estadoActual.pedidoActual.groupingBy { it }.eachCount()

                // Crear los objetos Detalle con los datos que pide tu tabla
                val listaDetalles = platillosAgrupados.map { (platillo, cantidadContada) ->
                    PedidoDetalle(
                        idPedido = idPedidoGenerado.toInt(),
                        idPlatillo = platillo.id,
                        nombrePedido = platillo.nombre,
                        precioPedido = platillo.precio,
                        cantidad = cantidadContada
                    )
                }

                pedidoRepository.insertDetalle(listaDetalles)

                _uiState.update {
                    it.copy(pedidoActual = emptyList(), total = 0.0)
                }
                println("Pedido #$idPedidoGenerado guardado con su detalle.")
            }
        }
    }
}