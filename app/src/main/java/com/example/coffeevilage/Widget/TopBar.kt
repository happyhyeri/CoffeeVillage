package com.example.coffeevilage.Widget

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
import com.example.coffeevilage.R


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
    title : String,
    navController: NavHostController?,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = colorResource( R.color.brown_3),
            titleContentColor = colorResource(R.color.brown_white),
            navigationIconContentColor = colorResource(R.color.brown_white)
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = colorResource(R.color.brown_white))
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
                        tint =  colorResource(R.color.brown_white)
                    )
                }
        },

    )
}


@Preview
@Composable
fun SimpleComposablePreview() {
    CommonTopAppBar("COFFEE VILLAGE", null, R.color.brown_1, R.drawable.icon_call, {})
}
