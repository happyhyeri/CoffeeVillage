package com.example.coffeevilage.Data

enum class OrderMethod(val label: String) {
    TAKEOUT("포장"),
    HERE("매장");
    override fun toString(): String {
        return label
    }

    companion object {
        fun fromLabel(label: String): OrderMethod? =
            OrderMethod.entries.find { it.label == label }
    }
}