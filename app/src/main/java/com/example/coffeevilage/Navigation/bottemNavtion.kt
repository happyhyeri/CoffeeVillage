package com.example.coffeevilage.Navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Badge
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BadgedBox

import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.coffeevilage.Data.BottomNavItem
import com.example.coffeevilage.R
import com.example.coffeevilage.ViewModel.CartViewModel
import com.example.coffeevilage.ViewModel.MenuViewModel
import com.example.coffeevilage.ViewModel.StateViewModel


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BottomNavigationBar(stateViewModel: StateViewModel, cartViewModel: CartViewModel, navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Order,
        BottomNavItem.Cart
    )
    val currentRoute = currentRoute(navController)
    val cartItemCount  by cartViewModel.cartItemCount.collectAsState()

    BottomNavigation(
        backgroundColor = if (currentRoute == BottomNavItem.Home.route) {
            colorResource(id = R.color.brown_3)
        } else {
            colorResource(id = R.color.brown_2)
        },
        contentColor = colorResource(id = R.color.brown_white),
        modifier = Modifier
            .drawBehind {
                val strokeWidth = 1.dp.toPx() // 테두리 너비
                val y = 0f // 상단 위치
                drawLine(
                    color = Color.White,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
            .navigationBarsPadding()
    ) {


        items.forEachIndexed  { index, item ->
            val iconRes = if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon
            val fontColor = if (currentRoute == item.route) Color(245, 245, 245) else Color(
                245f,
                245f,
                245f,
                0.5f
            )
            val fontBold = if (currentRoute == item.route) FontWeight.Bold else FontWeight.Normal
            val fontSize = if (currentRoute == item.route) 11.sp else 10.sp
            val cartCount = 0

                BottomNavigationItem(
                    selectedContentColor = Color.Transparent, // 선택된 상태의 색상
                    unselectedContentColor = Color.Transparent, // 선택되지 않은 상태의 색상
                    icon = {
                        if (index ==2) {
                            BadgedBox(
                                badge = {
                                    Badge() {
                                        Text(text = "$cartItemCount", color = Color.White)
                                    }
                                }
                            ) {
                                Image(
                                    painter = painterResource(id = iconRes),
                                    contentDescription = item.title,
                                    modifier = Modifier.size(25.dp)
                                )
                            }


                        } else {
                            Image(
                                painter = painterResource(id = iconRes),
                                contentDescription = item.title,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = fontColor,
                            fontSize = fontSize,
                            fontWeight = fontBold
                        )
                    },

                    selected = currentRoute == item.route,
                    onClick = {
                        if (item.title != "주문하기") {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }

                            stateViewModel.closeOrderDialog()
                        } else if(index == 1 && stateViewModel.orderMethod == null){
                            stateViewModel.showOrderDialog()
                        }else{

                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }


                        }

                    },
                )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

