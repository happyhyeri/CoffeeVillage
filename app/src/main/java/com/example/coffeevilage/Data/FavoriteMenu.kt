package com.example.coffeevilage.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteMenu(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val menuId: Int
)
