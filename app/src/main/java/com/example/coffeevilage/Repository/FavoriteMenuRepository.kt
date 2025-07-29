package com.example.coffeevilage.Repository

import com.example.coffeevilage.DAO.FavoriteMenuDao

class FavoriteMenuRepository(private val dao: FavoriteMenuDao) {

    suspend fun getAllFavorites() = dao.getAllFavorites()

}