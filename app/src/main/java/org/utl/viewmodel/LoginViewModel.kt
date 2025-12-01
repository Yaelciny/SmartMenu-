package org.utl.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.utl.db.UsuarioRepository

class LoginViewModel(private val repository: UsuarioRepository): ViewModel() {
    //Variable para mostrar error
    var hayError by mutableStateOf(false)

    init {
        // AL abrir la pantalla se crean los usuarios
        viewModelScope.launch {
            repository.generarUsuariosDefault()
        }
    }

    fun validarLogin(email: String, pass: String, alTerminar: (String) -> Unit){
        viewModelScope.launch {
            val usuario = repository.iniciarSesion(email,pass)

            if (usuario != null){
                hayError = false
                alTerminar(usuario.rol)
            }else{
                hayError = true
            }
        }
    }
}