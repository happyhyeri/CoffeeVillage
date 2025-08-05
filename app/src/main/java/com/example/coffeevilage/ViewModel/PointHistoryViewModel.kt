package com.example.coffeevilage.ViewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.coffeevilage.Data.CartItem
import com.example.coffeevilage.Data.Menu
import com.example.coffeevilage.Data.PointHistory
import com.example.coffeevilage.Data.PointType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.reflect.typeOf

class PointHistoryViewModel : ViewModel() {

    private val _point = MutableStateFlow(0)
    val point = _point.asStateFlow()

    val pointHistoryList = mutableStateListOf<PointHistory>()

    var cnt = 0 //일단 임의의 id값을 위해 있음. 외뷰 DB를 붙일시 AutoIncrease로 하기

    //추후 외부 DB 붙엿을때
    val totalPoint = 0
    val usedPoint = 0


    @RequiresApi(Build.VERSION_CODES.O)
    fun plusPoint(cnt: Int, orderMsg: String, multiplication: Boolean = false) {
        var value = 0
        if (multiplication) {
            value = cnt * 100 //주문 시 자동 적립
        } else {
            value = cnt //사용자 요청일 경우
        }
        _point.value += value


        //리스트 적립
         val pointHistory = makePointHistoryObj(value, orderMsg, PointType.SAVE)
         pointHistoryList.add(pointHistory)
    }


    fun minusPoint(point: Int) {
        _point.value -= point * 100
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun makePointHistoryObj(point: Int, orderMsg: String, type: PointType): PointHistory {
        val pointHistory = PointHistory(
            id = cnt++,
            date = LocalDate.now().toString(),
            point = point.toString(),
            type = type,
            message = orderMsg
        )
        return pointHistory
    }


}
