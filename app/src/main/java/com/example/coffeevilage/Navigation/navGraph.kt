package com.example.coffeevilage.Navigation

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import com.example.coffeevilage.ViewModel.PointHistoryViewModel
import com.example.coffeevilage.ViewModel.StateViewModel
import com.example.coffeevilage.ViewModel.UserViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    stateViewModel: StateViewModel,
    menuViewModel: MenuViewModel,
    userViewModel: UserViewModel,
    cartViewModel: CartViewModel,
    pointHistoryViewModel: PointHistoryViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isRegisteredUser = userViewModel.isPhoneNumberExist
    val paymentLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val success = result.data?.getBooleanExtra("paymentResult", false) ?: false
                val isCart = result.data?.getBooleanExtra("isCart", false) ?: false
                val orderCnt = result.data?.getIntExtra("orderCnt", 0) ?: 0
                val orderMsg = result.data?.getStringExtra("orderMsg") ?: ""
                if (success && isRegisteredUser) {
                    //포인트 관련 처리
                    Log.d("cnt", "$orderCnt")
                    pointHistoryViewModel.plusPoint(orderCnt, orderMsg, true)

                    //장바구니 경로 주문시
                    if (isCart) {
                        //카트아이템 -> 최근 주문
                        for (i in cartViewModel.cartList) {
                            menuViewModel.updateRecentOrderMenu(i.menu.id)

                        }
                        //장바구니 비워주기
                        cartViewModel.clearCartData()
                    }else{
                        menuViewModel.selectedMenu?.let { menuViewModel.updateRecentOrderMenu(it.id) }
                    }
                    //바로주문 -> 최근 주문
                }
            }
        }

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
            homeScreen(
                stateViewModel,
                menuViewModel,
                userViewModel,
                cartViewModel,
                pointHistoryViewModel,
                navController,
                paymentLauncher
            )
        }
        composable(BottomNavItem.Order.route) {
            orderScreen(stateViewModel, menuViewModel, cartViewModel, userViewModel ,paymentLauncher)
        }
        composable(BottomNavItem.Cart.route) {
            cartScreen(cartViewModel, stateViewModel,userViewModel, paymentLauncher)
        }
        composable(ScreenItem.PhoneRegisterScreen.route) {
            phoneRegisterScreen(stateViewModel, userViewModel, navController)
        }
        composable(ScreenItem.PointHistoryScreen.route) {
            pointHistoryScreen(pointHistoryViewModel, navController)
        }

    }
}



