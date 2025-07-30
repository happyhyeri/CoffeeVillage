package com.example.coffeevilage.Widget

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.coffeevilage.Data.OrderMethod
import com.example.coffeevilage.R
import com.example.coffeevilage.ViewModel.StateViewModel


@Composable
fun CommonTopAppBar(
    title: String,
    navController: NavHostController?,
    backgroundColor: Int,
    actionIcon: Int? = null,
    onClick: () -> Unit
) {

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }
        },
        actions = {
            if (actionIcon != null) {
                IconButton(onClick = {
                    onClick()
                }) {
                    Icon(
                        painter = painterResource(id = actionIcon),
                        contentDescription = null
                    )
                }
            }
        },
        backgroundColor = colorResource(id = backgroundColor),
        contentColor = Color.White,
        elevation = 0.dp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(
    title: String,
    navController: NavHostController?,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = colorResource(R.color.brown_3),
            titleContentColor = colorResource(R.color.brown_white),
            navigationIconContentColor = colorResource(R.color.brown_white)
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.brown_white)
                )
            }
        },

        navigationIcon = {
            IconButton(onClick = {
                navController?.popBackStack()
            }) {
                Icon(
                    painter = painterResource(R.drawable.outline_arrow_back_ios_24),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = colorResource(R.color.brown_white)
                )
            }
        },

        )
}

@Composable
fun CartTopAppBar(stateViewModel: StateViewModel) {

    val selectedOrderMethod =
            if (stateViewModel.orderMethod == null) {
                "주문 방식을 선택해주세요"
            } else {
                stateViewModel.orderMethod!!.toString()
            }

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = "CART", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }
        }, actions = {
            OrderMethodDropdown(selectedOrderMethod, { selectedOrderMethod ->
                stateViewModel.orderMethod = OrderMethod.fromLabel(selectedOrderMethod)
            })
        },
        backgroundColor = colorResource(id = R.color.brown_2),
        contentColor = Color.White,
        elevation = 0.dp
    )

}


@Preview
@Composable
fun SimpleComposablePreview() {

}
