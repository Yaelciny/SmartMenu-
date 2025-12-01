package org.utl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.utl.db.AppDataBase
import org.utl.db.PedidoRepository
import org.utl.db.PlatilloRepository
import org.utl.ui.theme.SmartMenuTheme
import org.utl.ui.theme.screens.LoginScreen
import org.utl.ui.theme.screens.MenuScreen
import org.utl.ui.theme.screens.MesasScreen
import org.utl.ui.theme.screens.ResumenScreen
import org.utl.viewmodel.MenuViewModel
import org.utl.viewmodel.MenuViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //INICIALIZAR LA BASE DE DATOS Y EL REPOSITORIO
        // Obtenemos la instancia de la BD
        val database = AppDataBase.getDatabase(applicationContext)
        // Creamos el repositorio con el DAO de esa BD
        val platilloRepo = PlatilloRepository(database.platilloDao())
        val pedidoRepo = PedidoRepository(
            database.pedidoDao(),
            database.pedidoDetalleDao() // <--- FALTABA ESTO
        )
        // Creamos la fábrica para el ViewModel
        val factory = MenuViewModelFactory(platilloRepo,pedidoRepo)
        setContent {
            SmartMenuTheme {
                val navController = rememberNavController()
                val sharedMenuViewModel: MenuViewModel = viewModel(factory = factory)
                NavHost(navController = navController, startDestination = "login"){
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                navController.navigate("mesas"){
                                    popUpTo("login"){ inclusive = true}
                                }
                            }
                        )
                    }
                    composable("menu") {
                        MenuScreen(
                            viewModel = sharedMenuViewModel,
                            onVerPedidoClick = {
                                // Navegamos a la nueva pantalla
                                navController.navigate("resumen")
                            }
                        )
                    }

                    composable("mesas") {
                        MesasScreen(
                            viewModel = sharedMenuViewModel,
                            onMesaSelected = {
                                //Al elegir la mesa, vamos al menu
                                navController.navigate("menu")
                            }
                        )
                    }

                    composable("resumen"){
                        ResumenScreen(
                            viewModel = sharedMenuViewModel,
                            onBackClick = {navController.popBackStack()},
                            onConfirmarClick = {
                                sharedMenuViewModel.confirmarPedido()
                                println("Pedido Enviado")
                                navController.popBackStack()
                            }
                        )
                    }
            }
        }
    }
}}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}