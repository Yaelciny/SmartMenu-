package org.utl.dao

import android.os.CpuUsageInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.utl.model.Platillo
import org.utl.model.Usuario

@Dao
interface UsuarioDao {

    //insertar usuario nuevo
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsuario(usuario: Usuario): Long

    @Query ("SELECT * FROM usuario")
    fun getAllUsuarios (): Flow<List<Usuario>>

    //funcion para iniciar sesion, se valida correo y contraseña
    @Query("SELECT * FROM usuario WHERE email = :email AND password = :password LIMIT 1")
    suspend fun iniciarSesion (email: String, password: String): Usuario?
}