package org.utl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.utl.db.PedidoRepository
import org.utl.db.PlatilloRepository

class MenuViewModelFactory(
    private val platilloRepository: PlatilloRepository,
    private val pedidoRepository: PedidoRepository
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MenuViewModel(platilloRepository,pedidoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}