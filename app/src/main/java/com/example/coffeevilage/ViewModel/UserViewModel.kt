package com.example.coffeevilage.ViewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class UserViewModel(application: Application) : AndroidViewModel(application) {
    val user_prefs = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    var registeredPhoneNumber by mutableStateOf("")
    var isPhoneNumberExist by mutableStateOf(false)

    //휴대폰번호 등록 여부
    fun isPhoneRegistered(): Boolean {
        val phone = user_prefs.getString("phone_number", "")
        return !phone.isNullOrBlank()
    }

    //저장된 휴대폰 번호 갖고오기
    fun getPhoneNumber(): String? {
        return user_prefs.getString("phone_number", null)
    }

    //휴대본 번호 등록
    fun savePhoneNumber(phone: String) {
        if(phone == ""){
            isPhoneNumberExist = false
        }else{
            isPhoneNumberExist = true //유효성 검사함수에서 걸러주기때문에 이런 로직 괜찮음
        }
        user_prefs.edit().putString("phone_number", phone).apply()
        registeredPhoneNumber = phone

    }

    init {
        isPhoneNumberExist = isPhoneRegistered()
        if (isPhoneNumberExist) {
            registeredPhoneNumber = getPhoneNumber().toString()

        } else {
            registeredPhoneNumber = ""

        }
    }


}