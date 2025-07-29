package com.example.coffeevilage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coffeevilage.DAO.FavoriteMenuDao
import com.example.coffeevilage.Data.FavoriteMenu

@Database(entities = [FavoriteMenu::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMenuDao(): FavoriteMenuDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "coffeeVillage_app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}