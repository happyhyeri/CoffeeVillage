package com.example.coffeevilage.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.coffeevilage.Data.OrderMethod

class StateViewModel : ViewModel() {

    //전화주문 전화번호
    val phoneNumber = "010-9525-1123"

    var isOrderNavBottomClicked by mutableStateOf(false)
    var orderMethod : OrderMethod? by mutableStateOf(null)

    var isValidPhoneNumber by mutableStateOf(false)
    var isNotValidPhoneNumber by mutableStateOf(false)

    //다이얼로그 관련 함수
    fun showOrderDialog(){
        isOrderNavBottomClicked = true
    }
    fun closeOrderDialog(){
        isOrderNavBottomClicked = false
    }

    //orderBottomSheet관련
    var showOrderBottomSheet by   mutableStateOf(false)


}
