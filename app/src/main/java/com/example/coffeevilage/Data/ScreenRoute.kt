package com.example.coffeevilage.Data

import com.example.coffeevilage.R

sealed class BottomNavItem (val title: String,val route: String, val selectedIcon: Int, val unselectedIcon: Int){
    object Home : BottomNavItem("홈", "home", R.drawable.icon_home_fill, R.drawable.icon_home_empty)
    object Order : BottomNavItem("주문하기", "order", R.drawable.icon_order_fill, R.drawable.icon_order_empty)
    object Cart : BottomNavItem("장바구니","Cart" ,R.drawable.icon_cart_fill, R.drawable.icon_cart_empty)
}
sealed class ScreenItem (val screen : String, val route: String){
    object PhoneRegisterScreen : ScreenItem( "phoneRegisterScreen","phoneRegisterScreen")
    object PointHistoryScreen : ScreenItem( "pointHistoryScreen","pointHistoryScreen")

}