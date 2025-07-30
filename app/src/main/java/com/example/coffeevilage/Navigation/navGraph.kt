package com.example.coffeevilage.Navigation

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.coffeevilage.Data.BottomNavItem
import com.example.coffeevilage.Data.ScreenItem
import com.example.coffeevilage.Screen.cartScreen
import com.example.coffeevilage.Screen.homeScreen
import com.example.coffeevilage.Screen.orderScreen
import com.example.coffeevilage.Screen.phoneRegisterScreen
import com.example.coffeevilage.Screen.pointHistoryScreen
import com.example.coffeevilage.ViewModel.CartViewModel
import com.example.coffeevilage.ViewModel.MenuViewModel
import com.example.coffeevilage.ViewModel.StateViewModel
import com.example.coffeevilage.ViewModel.UserViewModel

@Composable
fun NavGraph(
    stateViewModel: StateViewModel,
    menuViewModel: MenuViewModel,
    userViewModel: UserViewModel,
    cartViewModel: CartViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(BottomNavItem.Home.route) {
            homeScreen(stateViewModel, menuViewModel, userViewModel, cartViewModel, navController)
        }
        composable(BottomNavItem.Order.route) {
            orderScreen(stateViewModel, menuViewModel,cartViewModel)
        }
        composable(BottomNavItem.Cart.route) {
            cartScreen(cartViewModel,stateViewModel)
        }
        composable(ScreenItem.PhoneRegisterScreen.route) {
            phoneRegisterScreen(stateViewModel, userViewModel, navController)
        }
        composable(ScreenItem.PointHistoryScreen.route) {
            pointHistoryScreen(navController)
        }

    }
}



