package com.example.coffeevilage.Data

data class PointHistory(val id: Int, val date: String, val point: String, val type : PointType, val message : String )

enum class PointType {
    SAVE,
    USE
}
