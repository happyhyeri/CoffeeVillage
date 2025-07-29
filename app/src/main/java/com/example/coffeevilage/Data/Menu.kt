package com.example.coffeevilage.Data

data class Menu(val id: Int, val name: String, val price: Int, val imageRes: Int, val category: Category, val drinkType: DrinkType? = null, val isFavorite : Boolean =false)

enum class DrinkType{
    HOT, ICE
}
enum class Category{
    COFFEE,
    NONCOFFEE,
    BEVERAGE,
    TEA,
    DESSERT
}

