package com.example.coffeevilage.Widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevilage.R


@Composable
fun ImageButton(imgRes: Int, padding: Int, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(padding.dp)
    ) {
        Icon(
            painter = painterResource(id = imgRes),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Composable
fun PointRadioButton(
    options: List<String>,
    selectedOption: String,
    onOptionClick: (String) -> Unit
) {
    val color = colorResource(R.color.dark_brown)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
    ) {

        Text(
            text = "내역구분",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 8.dp),
            color = color
        )

        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 12.dp)
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { onOptionClick(option) },
                    colors = RadioButtonDefaults.colors(color)
                )
                Text(
                    text = option,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp),
                    color = color
                )
            }
        }
    }
}

@Composable
fun PeriodSelectToggleButtons(
    periodOptions: List<String>,
    periodSelectedOption: String,
    onPeriodOptionClick: (String) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        periodOptions.forEach { periodOptions ->
            Surface(
                shape = RoundedCornerShape(50),
                color = if (periodSelectedOption == periodOptions) colorResource(R.color.dark_brown) else Color.White,
                border = BorderStroke(1.dp, color = colorResource(R.color.dark_brown)),
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { onPeriodOptionClick(periodOptions)})
            ) {
                Text(
                    text = periodOptions,
                    color = if (periodSelectedOption == periodOptions) Color.White else colorResource(
                        R.color.dark_brown
                    ),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
