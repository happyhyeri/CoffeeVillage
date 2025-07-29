package com.example.coffeevilage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coffeevilage.Data.BottomNavItem
import com.example.coffeevilage.Data.ScreenItem
import com.example.coffeevilage.Navigation.BottomNavigationBar
import com.example.coffeevilage.Navigation.NavGraph
import com.example.coffeevilage.ViewModel.CartViewModel
import com.example.coffeevilage.ViewModel.MenuViewModel
import com.example.coffeevilage.ViewModel.StateViewModel
import com.example.coffeevilage.ViewModel.UserViewModel
import com.example.coffeevilage.Widget.OrderDialog

class MainActivity : ComponentActivity() {
    private val stateViewModel: StateViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }
    private val menuViewModel: MenuViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MainScreen(stateViewModel, menuViewModel,userViewModel,cartViewModel )
        }
    }
}

//전역적으로 NavController 관리하기 위해서
//val navController = LocalNavController.current이렇게 사용하기 위해서
val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController provided")
}

@Composable
fun MainScreen(stateViewModel: StateViewModel, menuViewModel: MenuViewModel, userViewModel: UserViewModel, cartViewModel: CartViewModel) {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        Scaffold(
            bottomBar = {
                val currentDestination =
                    navController.currentBackStackEntryAsState().value?.destination
                if (currentDestination?.route != ScreenItem.PhoneRegisterScreen.route && currentDestination?.route != ScreenItem.PointHistoryScreen.route) {
                    BottomNavigationBar(stateViewModel,cartViewModel, navController)
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                if (stateViewModel.isOrderNavBottomClicked) {
                    OrderDialog(
                        onDismiss = { stateViewModel.closeOrderDialog() },
                        onTakeOutClick = {
                            stateViewModel.closeOrderDialog()
                            navController.navigate(BottomNavItem.Order.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                          menuViewModel.initOrderTab()
                        },
                        onDrinkHereClick = {
                            stateViewModel.closeOrderDialog()
                            navController.navigate(BottomNavItem.Order.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                            menuViewModel.initOrderTab()

                        }
                    )
                }
                NavGraph(
                    stateViewModel = stateViewModel,
                    menuViewModel = menuViewModel ,
                    userViewModel = userViewModel,
                    cartViewModel = cartViewModel,
                    navController = navController,
                    modifier = Modifier.padding(0.dp),
                )
            }
        }
    }
}


