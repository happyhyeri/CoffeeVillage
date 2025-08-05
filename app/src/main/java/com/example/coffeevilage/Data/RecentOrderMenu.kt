package com.example.coffeevilage.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "recent_order")
data class RecentOrderMenu(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val menuId: Int,
    val date: Long
)
