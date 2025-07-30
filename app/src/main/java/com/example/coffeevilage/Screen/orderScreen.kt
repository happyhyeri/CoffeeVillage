package com.example.coffeevilage.Screen

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.coffeevilage.Data.CartItem
import com.example.coffeevilage.Data.Category
import com.example.coffeevilage.Data.Menu
import com.example.coffeevilage.R
import com.example.coffeevilage.ViewModel.CartViewModel
import com.example.coffeevilage.ViewModel.MenuViewModel
import com.example.coffeevilage.ViewModel.StateViewModel
import com.example.coffeevilage.Widget.CallDialog
import com.example.coffeevilage.Widget.CommonTopAppBar
import com.example.coffeevilage.Widget.HotIceTab
import com.example.coffeevilage.Widget.OrderDetailBottomSheet

import com.example.coffeevilage.Widget.OrderDetailPage_NoShotOption
import com.example.coffeevilage.Widget.OrderDetailPage_OnlyCnt
import com.example.coffeevilage.Widget.OrderMenuCard
import com.example.coffeevilage.Widget.QuantitySelectorSection
import com.example.coffeevilage.Widget.ScrollTabs

@Composable
fun orderScreen(
    stateViewModel: StateViewModel,
    menuViewModel: MenuViewModel,
    cartViewModel: CartViewModel
) {
    var showCallDialog by remember { mutableStateOf(false) }
    val tabs = listOf("커피", "논커피", "음료", "차", "디저트")

    val context = LocalContext.current

    val selectedTabIndex by menuViewModel.selectedTabIndex.collectAsState()
    val selectedTemperature by menuViewModel.selectedTemperature.collectAsState()
    val filteredMenu by menuViewModel.filteredMenu.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.brown_2))
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            CommonTopAppBar(
                "ORDER",
                null,
                R.color.brown_2,
                R.drawable.icon_call,
                { showCallDialog = true })
        }
        ScrollTabs(tabs, selectedTabIndex) { index -> menuViewModel.updateTabIndex(index) }
        if(selectedTabIndex!=4) {
            HotIceTab(selectedTemperature) { tab -> menuViewModel.updateTemperature(tab) }
        }
        MenuListSection(filteredMenu, stateViewModel, menuViewModel)


    }
    if (stateViewModel.showOrderBottomSheet && menuViewModel.selectedMenu != null) {
        OrderDetailBottomSheet(stateViewModel, menuViewModel, cartViewModel)
    }
    if (showCallDialog) {
        CallDialog(onClickYes = {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${stateViewModel.phoneNumber}")
            }
            context.startActivity(intent)
        }, onClickNo = { showCallDialog = false })
    }

}

@Composable
fun MenuListSection(
    menus: List<Menu>,
    stateViewModel: StateViewModel,
    menuViewModel: MenuViewModel
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        state = gridState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD6C4AF)),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
       // verticalItemSpacing = 8.dp,
        contentPadding = PaddingValues(8.dp)
    ) {
        items(menus, key = {it.id}) { menu ->
            OrderMenuCard(
                menu,
                menuViewModel,
                showOrderBottomSheet = { stateViewModel.showOrderBottomSheet = true },
                clickedMenu = {Menu ->
                    menuViewModel.selectedMenu = Menu}
            )
        }
    }
}




