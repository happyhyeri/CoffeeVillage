package com.example.coffeevilage.Data

data class PointHistory( val date: String, val point: String, val type : PointType )

enum class PointType {
    SAVE,
    USE
}
