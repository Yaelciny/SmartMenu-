package org.utl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.utl.db.PlatilloRepository
import org.utl.model.Platillo

class AdminViewModel(private val repository: PlatilloRepository): ViewModel() {
    //vemos la lista de platillo
    val listaPlatillo: StateFlow<List<Platillo>> = repository.allPlatillos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(50000),
            initialValue = emptyList()
        )

    fun agregarPlatillo(nombre: String, precio: Double, ){
        viewModelScope.launch {
            val nuevoPlatillo = Platillo(
                nombre = nombre,
                precio = precio,
                disponible = true
            )
            repository.insertPlatillo(nuevoPlatillo)
        }
    }
}