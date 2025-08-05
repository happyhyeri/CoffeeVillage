package com.example.coffeevilage.Screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.coffeevilage.Data.PointHistory
import com.example.coffeevilage.Data.PointType
import com.example.coffeevilage.R
import com.example.coffeevilage.ViewModel.PointHistoryViewModel
import com.example.coffeevilage.Widget.BackTopBar
import com.example.coffeevilage.Widget.PeriodSelectToggleButtons
import com.example.coffeevilage.Widget.PointRadioButton
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PointHistoryScreen(pointHistoryViewModel: PointHistoryViewModel, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.brown_3))
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            BackTopBar("포인트 조회", navController = navController)
        }
        Column(modifier = Modifier.fillMaxWidth())
        {
            pointTotalSection(pointHistoryViewModel)
            pointHistorySection(pointHistoryViewModel)
        }


    }
}

@Composable
fun pointTotalSection(pointHistoryViewModel: PointHistoryViewModel) {
    val point = pointHistoryViewModel.point.collectAsState()

    val fontWeight = FontWeight.Bold
    val fontColor = colorResource(R.color.brown_white)
    val horizontalPaddingValue = 20.dp
    val verticalPaddingValue = 10.dp


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPaddingValue, vertical = verticalPaddingValue),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "사용 가능한 포인트",
                fontWeight = fontWeight,
                color = fontColor,
                fontSize = 17.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${point.value}", fontWeight = fontWeight, color = fontColor, fontSize = 17.sp)
                Text(
                    "P",
                    fontWeight = fontWeight,
                    color = fontColor,
                    modifier = Modifier.padding(start = 7.dp), fontSize = 17.sp
                )
            }
        }
        //구분선
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(1.dp)
                .background(colorResource(R.color.brown_white))
                .padding(4.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()

                .padding(horizontal = horizontalPaddingValue, vertical = verticalPaddingValue),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "총 적립 포인트 ", fontWeight = FontWeight.SemiBold, color = fontColor)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${pointHistoryViewModel.usedPoint+ point.value}", fontWeight = fontWeight, color = fontColor)
                Text(
                    "P",
                    fontWeight = fontWeight,
                    color = fontColor,
                    modifier = Modifier.padding(start = 7.dp)
                )
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = horizontalPaddingValue,
                    vertical = verticalPaddingValue / 2
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "사용 포인트 ", fontWeight = FontWeight.SemiBold, color = fontColor)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${pointHistoryViewModel.usedPoint}", fontWeight = fontWeight, color = fontColor)
                Text(
                    "P",
                    fontWeight = fontWeight,
                    color = fontColor,
                    modifier = Modifier.padding(start = 7.dp)
                )
            }

        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun pointHistorySection(pointHistoryViewModel: PointHistoryViewModel) {
    val options = listOf("전체", "적립", "사용")
    val periodOptions = listOf("일주일", "1개월", "3개월", "6개월")

    var selectedOption by remember { mutableStateOf("전체") }
    var periodSelectedOption by remember { mutableStateOf("일주일") }


    val filteredList = remember(pointHistoryViewModel.pointHistoryList, selectedOption, periodSelectedOption) {
        val now = LocalDate.now()

        pointHistoryViewModel.pointHistoryList.filter { item ->
            val itemDate = LocalDate.parse(item.date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            val matchesType = when (selectedOption) {
                "전체" -> true
                "적립" -> item.type == PointType.SAVE
                "사용" -> item.type == PointType.USE
                else -> true
            }

            val withinPeriod = when (periodSelectedOption) {
                "일주일" -> itemDate in now.minusWeeks(1)..now
                "1개월" -> itemDate in now.minusMonths(1)..now
                "3개월" -> itemDate in now.minusMonths(3)..now
                "6개월" -> itemDate in now.minusMonths(6)..now
                else -> true
            }

            matchesType && withinPeriod
        }
    }


    PointRadioButton(
        options = options,
        selectedOption = selectedOption,
        onOptionClick = { option -> selectedOption = option })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.brown_white))
    ) {
        PeriodSelectToggleButtons(
            periodOptions = periodOptions,
            periodSelectedOption = periodSelectedOption,
            onPeriodOptionClick = { PeriodOption -> periodSelectedOption = PeriodOption })
        PointHistoryList(filteredList)

    }


}

@Composable
fun PointHistoryList(histories: List<PointHistory>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(histories) { history ->
            PointHistoryItem(history)
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(horizontal = 7.dp))

        }
    }
}

@Composable
fun PointHistoryItem(history: PointHistory) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .background(colorResource(R.color.brown_white), RoundedCornerShape(2.dp))
            .padding(12.dp)
    ) {


        Text(
            text = history.date,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Gray
        )


        Row(
            modifier = Modifier.fillMaxWidth().padding(top=7.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = history.message,
                fontSize = 13.sp,
                color = colorResource(R.color.dark_brown),
                fontWeight = FontWeight.SemiBold
            )


            Text(
                text = (if (history.type == PointType.SAVE) "+" else "-") + history.point,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = if (history.type == PointType.SAVE) Color.Blue else Color.Red,
            )
        }

    }
}

@Preview
@Composable
fun showPointScreen() {
    val navController = rememberNavController()

}