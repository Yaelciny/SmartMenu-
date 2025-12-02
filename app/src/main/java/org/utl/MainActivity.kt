package org.utl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.utl.db.AppDataBase
import org.utl.db.ClienteRepository
import org.utl.db.InsumoRepository
import org.utl.db.PedidoRepository
import org.utl.db.PlatilloRepository
import org.utl.db.UsuarioRepository
import org.utl.ui.theme.SmartMenuTheme
import org.utl.ui.theme.screens.AdminInsumoScreen
import org.utl.ui.theme.screens.AdminScreen
import org.utl.ui.theme.screens.ClienteScreen
import org.utl.ui.theme.screens.CocinaScreen
import org.utl.ui.theme.screens.LoginScreen
import org.utl.ui.theme.screens.MenuScreen
import org.utl.ui.theme.screens.MesasScreen
import org.utl.ui.theme.screens.ResumenScreen
import org.utl.viewmodel.AdminViewModel
import org.utl.viewmodel.MenuViewModel
import org.utl.viewmodel.AppViewModelFactory
import org.utl.viewmodel.CocinaViewModel
import org.utl.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //INICIALIZAR LA BASE DE DATOS Y EL REPOSITORIO
        // Obtenemos la instancia de la BD
        val database = AppDataBase.getDatabase(applicationContext)
        // Creamos el repositorio con el DAO de esa BD
        val platilloRepo = PlatilloRepository(database.platilloDao())
        val pedidoRepo = PedidoRepository(
            database,
            database.pedidoDao(),
            database.pedidoDetalleDao(), // <--- FALTABA ESTO
            database.platilloDao()
        )
        val usuarioRepo = UsuarioRepository(database.usuarioDao())
        val insumoRepo = InsumoRepository(database.insumoDao())
        val clienteRepo = ClienteRepository(database.clienteDao())

        // Creamos la fábrica para el ViewModel
        val factory = AppViewModelFactory(platilloRepo,pedidoRepo,insumoRepo,usuarioRepo,clienteRepo)
        setContent {
            SmartMenuTheme {
                val navController = rememberNavController()
                //Todos los ViewModels
                val sharedMenuViewModel: MenuViewModel = viewModel(factory = factory)
                val loginViewModel: LoginViewModel = viewModel(factory = factory)
                val cocinaViewModel: CocinaViewModel = viewModel(factory = factory)
                val adminViewModel: AdminViewModel = viewModel(factory = factory)
                NavHost(navController = navController, startDestination = "login"){
                    composable("login") {
                        LoginScreen(
                            viewModel = loginViewModel,
                            onLoginSuccess = { rolDetectado ->
                                // Si es Mesero o Admin -> Mesas
                                // Si fuera Cocinero -> Cocina
                                val destino = when(rolDetectado) {
                                    "Cocinero" -> "cocina"
                                    "Administrador" -> "admin"
                                    else -> "mesas"
                                }
                                navController.navigate(destino){
                                    popUpTo("login"){ inclusive = true}
                                }
                            }
                        )
                    }
                    composable("admin") {
                        AdminScreen(
                            viewModel = adminViewModel,
                            onLogout = {
                                navController.navigate("login"){
                                    popUpTo(0){ inclusive = true }
                                }
                            },
                            onVerInsumos = {
                                navController.navigate("insumo")
                            }
                        )
                    }

                    composable("insumo") {
                        AdminInsumoScreen (
                            viewModel = adminViewModel,
                            onLogout = {
                                navController.navigate("admin"){
                                    popUpTo(0) {inclusive = true}
                                }
                            }
                        )
                    }
                    composable("menu") {
                        MenuScreen(
                            viewModel = sharedMenuViewModel,
                            onVerPedidoClick = {
                                navController.navigate("resumen")
                            },
                            onLogout = {
                                navController.navigate("mesas")
                            }
                        )
                    }

                    composable("mesas") {
                        MesasScreen(
                            viewModel = sharedMenuViewModel,
                            onMesaSelected = {
                                //Al elegir la mesa, vamos al menu
                                navController.navigate("menu")
                            },
                            //Logica para cerrar sesion
                            onLgout = {
                                navController.navigate("login"){
                                    popUpTo(0) {inclusive = true}
                                }
                            },
                            onVerClientes = {
                                navController.navigate("clientes")
                            }
                        )
                    }
                    composable("cocina") {
                        CocinaScreen(
                            viewModel = cocinaViewModel,
                            onLogout = {
                                navController.navigate("login"){
                                    popUpTo(0) {inclusive = true}
                                }
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
                                navController.popBackStack()
                            }
                        )
                    }
                    composable("clientes") {
                        ClienteScreen(
                            viewModel = sharedMenuViewModel,
                            onBack = {navController.popBackStack()}
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