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
    fun updateCartItem(
        cartItem: CartItem,
        cnt: Int,
        optionAppliedPrice: Int,
        plus: Boolean = true
    ) {

        val index =
            cartList.indexOfFirst { it.menu.id == cartItem.menu.id && it.optionAppliedPrice == optionAppliedPrice }
        Log.d("index", "${index}")
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

    fun addCartItem(
        menu: Menu,
        quantaty: Int,
        optionAppliedPrice: Int,
        sizeOption: String?,
        shotOption: String?
    ) {
        val existMenu = cartList.find {
            if (sizeOption != null && shotOption != null) { //사이즈, 샷
                it.menu.id == menu.id && it.optionAppliedPrice == optionAppliedPrice && it.size.toString() == sizeOption && it.shot.toString() == shotOption
            } else if (sizeOption != null) { //사이즈
                it.menu.id == menu.id && it.optionAppliedPrice == optionAppliedPrice && it.size.toString() == sizeOption
            } else { //only 카운트
                it.menu.id == menu.id && it.optionAppliedPrice == optionAppliedPrice
            }
        }
        if (existMenu != null) {
            val updatedItem = existMenu.copy(quantity = existMenu.quantity + quantaty)
            cartList[cartList.indexOf(existMenu)] = updatedItem
        } else {
            val cartItem : CartItem
            if (sizeOption != null && shotOption != null) {
                cartItem = CartItem(
                    menu = menu,
                    quantity = quantaty,
                    optionAppliedPrice = optionAppliedPrice,
                    size = Size.fromLabel(sizeOption),
                    shot = Shot.fromLabel(shotOption)
                )

            } else if (sizeOption != null) {

                cartItem = CartItem(
                    menu = menu,
                    quantity = quantaty,
                    optionAppliedPrice = optionAppliedPrice,
                    size = Size.fromLabel(sizeOption),
                    shot = null
                )
            } else {
                cartItem = CartItem(
                    menu = menu,
                    quantity = quantaty,
                    optionAppliedPrice = optionAppliedPrice,
                    size = null,
                    shot = null
                )
            }
            cartList.add(cartItem)
        }
    }

    fun removeCartItem(cartItem: CartItem) {
        if(cartItem.size != null && cartItem.shot != null){
            cartList.removeIf { it.menu.id == cartItem.menu.id && it.optionAppliedPrice == cartItem.optionAppliedPrice && it.size == cartItem.size && it.shot == cartItem.shot }
        }else if(cartItem.size != null ){
            cartList.removeIf { it.menu.id == cartItem.menu.id && it.optionAppliedPrice == cartItem.optionAppliedPrice && it.size == cartItem.size}
        }else{
            cartList.removeIf { it.menu.id == cartItem.menu.id && it.optionAppliedPrice == cartItem.optionAppliedPrice}
        }
    }
}