package com.example.coffeevilage.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coffeevilage.Data.FavoriteMenu

@Dao
interface FavoriteMenuDao {

    @Query("SELECT * FROM favorite")
    suspend fun getAllFavorites(): List<FavoriteMenu>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteMenu)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteMenu)

    @Query("SELECT * FROM favorite WHERE menuId = :menuId LIMIT 1")
    suspend fun getFavoriteByMenuId(menuId: Int): FavoriteMenu?
}