package com.example.coffeevilage

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import com.example.coffeevilage.Data.OrderMethod
import com.example.coffeevilage.Data.ScreenItem
import com.example.coffeevilage.Navigation.BottomNavigationBar
import com.example.coffeevilage.Navigation.NavGraph
import com.example.coffeevilage.Payment.TotalPaymentActivity
import com.example.coffeevilage.ViewModel.CartViewModel
import com.example.coffeevilage.ViewModel.MenuViewModel
import com.example.coffeevilage.ViewModel.PointHistoryViewModel
import com.example.coffeevilage.ViewModel.StateViewModel
import com.example.coffeevilage.ViewModel.UserViewModel
import com.example.coffeevilage.Widget.OrderDialog

class MainActivity : ComponentActivity() {
    private lateinit var paymentLauncher: ActivityResultLauncher<Intent>

    private val stateViewModel: StateViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }
    private val menuViewModel: MenuViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }
    private val pointHistoryViewModel : PointHistoryViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
         paymentLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val success = result.data?.getBooleanExtra("paymentResult", false) ?: false
                if(success){
                    //장바구니 비워주기
                    Log.d("하잇","${success}")
                    cartViewModel.cartList.clear()
                }
            }
        }


        setContent {
            MainScreen(stateViewModel, menuViewModel,userViewModel,cartViewModel,pointHistoryViewModel )
        }
    }
}

//전역적으로 NavController 관리하기 위해서
//val navController = LocalNavController.current이렇게 사용하기 위해서
val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController provided")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(stateViewModel: StateViewModel, menuViewModel: MenuViewModel, userViewModel: UserViewModel, cartViewModel: CartViewModel, pointHistoryViewModel: PointHistoryViewModel) {
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
                if (stateViewModel.isOrderNavBottomClicked && stateViewModel.orderMethod == null) {
                    OrderDialog(
                        onDismiss = { stateViewModel.closeOrderDialog() },
                        onTakeOutClick = {
                            stateViewModel.closeOrderDialog()
                            navController.navigate(BottomNavItem.Order.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                            stateViewModel.orderMethod = OrderMethod.TAKEOUT
                          menuViewModel.initOrderTab()
                        },
                        onDrinkHereClick = {
                            stateViewModel.closeOrderDialog()
                            navController.navigate(BottomNavItem.Order.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                            stateViewModel.orderMethod =  OrderMethod.HERE
                            menuViewModel.initOrderTab()
                        }
                    )
                }
                NavGraph(
                    stateViewModel = stateViewModel,
                    menuViewModel = menuViewModel ,
                    userViewModel = userViewModel,
                    cartViewModel = cartViewModel,
                    pointHistoryViewModel = pointHistoryViewModel,
                    navController = navController,
                    modifier = Modifier.padding(0.dp),
                )
            }
        }
    }
}


