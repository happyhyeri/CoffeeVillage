package com.example.coffeevilage.Widget

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevilage.R
import com.example.coffeevilage.ViewModel.MenuViewModel

@Composable
fun TabText(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val baseColor = colorResource(R.color.brown_white)
    val textColor by animateColorAsState(
        targetValue = if (isSelected) baseColor else baseColor.copy(alpha = 0.5f),
        label = "tabColorAnimation"
    )
    val interactionSource = remember { MutableInteractionSource() }

    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = textColor,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
    )
}

@Composable
fun ScrollTabs(tabs: List<String>, selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        backgroundColor = colorResource(id = R.color.brown_2),
        contentColor = Color.White,

    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index)
                          },
                text = {
                    androidx.compose.material.Text(
                        text = tab,
                        color = Color.White,
                        fontWeight =  if (selectedTabIndex == index) FontWeight.Bold else FontWeight.SemiBold,
                        fontSize = if (selectedTabIndex == index) 16.sp else 13.sp
                    )
                }
            )
        }
    }
}

@Composable
fun HotIceTab(selectedTab : String ,onTabSelected: (String) -> Unit ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        listOf("ICE", "HOT").forEach { tab ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        color = if (selectedTab == tab)
                            Color(245f,245f,245f,0.25f)
                        else
                        colorResource(R.color.brown_2)
                    )
                    .clickable { onTabSelected(tab)},
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (tab == "ICE") "아이스(ICE)" else "핫(HOT)",
                    color = Color.White,
                    fontWeight = if (selectedTab == tab)FontWeight.Bold else FontWeight.SemiBold
                )
            }
        }
    }
    Divider(
        color = Color(245f,245f,245f,0.25f),
        thickness = 0.5.dp
    )

}