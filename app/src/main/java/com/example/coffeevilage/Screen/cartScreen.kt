package com.example.coffeevilage.Screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevilage.Data.CartItem
import com.example.coffeevilage.Data.Category
import com.example.coffeevilage.Data.DrinkType
import com.example.coffeevilage.Data.Menu
import com.example.coffeevilage.Data.ScreenItem
import com.example.coffeevilage.R
import com.example.coffeevilage.ViewModel.CartViewModel
import com.example.coffeevilage.ViewModel.StateViewModel
import com.example.coffeevilage.Widget.CallDialog
import com.example.coffeevilage.Widget.CartItmeCard
import com.example.coffeevilage.Widget.CartTopAppBar
import com.example.coffeevilage.Widget.CommonTopAppBar

@Composable
fun cartScreen(cartViewModel: CartViewModel, stateViewModel: StateViewModel) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.brown_2))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CartTopAppBar(stateViewModel)
        Column(Modifier.weight(3f)) {
            CartItemList(cartViewModel)
        }
        Column(Modifier.weight(1f)) {
            TotalSection(cartViewModel)
        }
    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TotalSection(cartViewModel: CartViewModel) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 3.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "총 수량",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = colorResource(R.color.brown_white)
            )
            Row() {
                Text(
                    text = "${cartViewModel.cartItemCount.value}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = colorResource(R.color.brown_white)
                )
                Text(
                    text = " 개",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = colorResource(R.color.brown_white)
                )
            }

        }

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 3.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "총 금액",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = colorResource(R.color.brown_white)
            )
            Row() {
                Text(
                    text = "${cartViewModel.totalPrice}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = colorResource(R.color.brown_white)
                )
                Text(
                    text = " 원",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = colorResource(R.color.brown_white)
                )
            }

        }
        Spacer(modifier = Modifier.height(7.dp))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.dark_brown),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)

        ) {
            Text(
                text = "주문하기",
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.brown_white)
            )
        }

    }

}


@Composable
fun CartItemList(cartViewModel: CartViewModel) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn(modifier = Modifier.background(color = colorResource(R.color.brown_2))) {
            items(cartViewModel.cartList) { cart ->
                CartItmeCard(cart, Modifier.fillMaxWidth(), cartViewModel)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

