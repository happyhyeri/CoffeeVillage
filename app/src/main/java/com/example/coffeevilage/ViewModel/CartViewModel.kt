package com.example.coffeevilage.ViewModel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.coffeevilage.Data.CartItem
import com.example.coffeevilage.Data.Category
import com.example.coffeevilage.Data.DrinkType
import com.example.coffeevilage.Data.Menu
import com.example.coffeevilage.Data.Shot
import com.example.coffeevilage.Data.Size
import com.example.coffeevilage.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel : ViewModel() {


    private val _cartItemCount = MutableStateFlow(0)
    val cartItemCount = _cartItemCount.asStateFlow()

    private val _totalPrice = MutableStateFlow(0)
    val atotalPrice = _totalPrice.asStateFlow()

    var totalPrice by mutableStateOf(0)
    val cartList = mutableStateListOf<CartItem>()

    fun managePriceAndQuantityCartItem(cnt: Int, price: Int, plus: Boolean = true) {

        if (plus) {
            _cartItemCount.value += cnt
            totalPrice += price
        } else {
            _cartItemCount.value -= cnt
            totalPrice -= price
        }
    }

    //따로 뺀 이유: managePriceAndQuantityCartItem의 경우엔 order과 cart에서 공용으로 사용.
    fun updateCartItem(cartItem: CartItem, cnt: Int, optionAppliedPrice: Int, plus: Boolean = true) {

        val index = cartList.indexOfFirst { it.menu.id == cartItem.menu.id && it.optionAppliedPrice == optionAppliedPrice }
        Log.d("index","${index}")
        var updatedItem: CartItem
        if (index != -1) {
            if (plus) {
                val beforeItem = cartList[index]
                updatedItem = beforeItem.copy(
                    quantity = cartItem.quantity + cnt
                )
            } else {
                val beforeItem = cartList[index]
                updatedItem = beforeItem.copy(
                    quantity = cartItem.quantity - cnt
                )
            }
            cartList[index] = updatedItem
        }

    }
    fun addCartItem(menu : Menu, quantaty: Int, optionAppliedPrice : Int , sizeOption: String, shotOption : String){
        val existMenu = cartList.find { it.menu.id == menu.id && it.optionAppliedPrice == optionAppliedPrice && it.size.toString() == sizeOption && it.shot.toString() ==shotOption}

        if(existMenu != null){
            val updatedItem = existMenu.copy(quantity = existMenu.quantity + quantaty)
            cartList[cartList.indexOf(existMenu)] = updatedItem
        }else{
            Size.fromLabel(sizeOption)?.let { Shot.fromLabel(shotOption)?.let { it1 -> CartItem(menu = menu, quantity = quantaty, optionAppliedPrice = optionAppliedPrice, size = it, shot = it1) } }
                ?.let { cartList.add(it) }
        }
    }

    fun removeCartItem(cartItem: CartItem) {
        cartList.removeIf { it.menu.id == cartItem.menu.id && it.optionAppliedPrice == cartItem.optionAppliedPrice && it.size == cartItem.size && it.shot == cartItem.shot }
    }
}