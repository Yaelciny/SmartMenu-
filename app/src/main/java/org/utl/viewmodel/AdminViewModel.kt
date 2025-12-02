package org.utl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.utl.db.InsumoRepository
import org.utl.db.PlatilloRepository
import org.utl.model.Insumo
import org.utl.model.Platillo

class AdminViewModel(private val repository: PlatilloRepository,
    private val insumoRepository: InsumoRepository): ViewModel() {
    //vemos la lista de platillo
    val listaPlatillo: StateFlow<List<Platillo>> = repository.allPlatillos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(50000),
            initialValue = emptyList()
        )

    fun agregarPlatillo(nombre: String, precio: Double, stock: Int){
        viewModelScope.launch {
            val nuevoPlatillo = Platillo(
                nombre = nombre,
                precio = precio,
                disponible = stock > 0,
                stock = stock
            )
            repository.insertPlatillo(nuevoPlatillo)
        }
    }

    val listaInsumo: StateFlow<List<Insumo>> = insumoRepository.allInsumos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(50000),
            initialValue = emptyList()
        )

    fun agregarInsumo (nombre: String, stock: Int, unidad: String){
        viewModelScope.launch {
            val nuevoInsumo = Insumo(
                nombre = nombre,
                stock = stock,
                unidad = unidad
            )
            insumoRepository.insertInsumo(nuevoInsumo)
        }
    }
}