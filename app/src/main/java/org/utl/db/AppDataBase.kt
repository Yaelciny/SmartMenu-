package org.utl.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.utl.dao.InsumoDao
import org.utl.dao.PedidoDao
import org.utl.dao.PedidoDetalleDao
import org.utl.dao.PlatilloDao
import org.utl.dao.UsuarioDao
import org.utl.model.Insumo
import org.utl.model.Pedido
import org.utl.model.PedidoDetalle
import org.utl.model.Platillo
import org.utl.model.Usuario

// Agrega aki tus las otras cuando esten listas
// por ahora solo con Platillo
@Database(entities = [Platillo::class, Usuario::class, Pedido::class, PedidoDetalle::class, Insumo::class],
    version = 2)
abstract class AppDataBase : RoomDatabase(){

    abstract fun platilloDao(): PlatilloDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun pedidoDao(): PedidoDao
    abstract fun pedidoDetalleDao(): PedidoDetalleDao
    abstract fun insumoDao(): InsumoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "smartmenu_database" // Nombre del archivo de la BD en el celular
                )
                    .fallbackToDestructiveMigration() // Si cambias la BD, borra y crea nueva
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}