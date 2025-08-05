package com.example.coffeevilage.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coffeevilage.Data.RecentOrderMenu

@Dao
interface RecentOrderMenuDao {

    @Query("SELECT * FROM recent_order")
    suspend fun getAllRecentOrderMenu(): List<RecentOrderMenu>

    @Query("SELECT * FROM recent_order ORDER BY date DESC")
    suspend fun getAllRecentOrderMenu_OrderBy_date(): List<RecentOrderMenu>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentOrderMenu(favorite: RecentOrderMenu)

    @Delete
    suspend fun deleteRecentOrderMenus(recentOrders: List<RecentOrderMenu>)

    @Delete
    suspend fun deleteRecentOrderMenu(recentOrders: RecentOrderMenu)


    @Query("SELECT * FROM recent_order WHERE menuId = :menuId LIMIT 1")
    suspend fun getRecentOrderMenuByMenuId(menuId: Int): RecentOrderMenu?

}