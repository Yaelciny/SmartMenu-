package org.utl.db

import kotlinx.coroutines.flow.Flow
import org.utl.dao.PlatilloDao
import org.utl.dao.UsuarioDao
import org.utl.model.Platillo
import org.utl.model.Usuario

class UsuarioRepository (private val usuarioDao: UsuarioDao) {

    val allUsuarios: Flow<List<Usuario>> = usuarioDao.getAllUsuarios()

    suspend fun insertUsuario (usuario: Usuario): Long{
        return usuarioDao.insertUsuario(usuario)
    }

    suspend fun iniciarSesion (email: String, password: String): Usuario? {
        return usuarioDao.iniciarSesion(email,password)
    }


}