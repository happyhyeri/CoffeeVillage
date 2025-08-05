package com.example.coffeevilage.Repository

import com.example.coffeevilage.DAO.RecentOrderMenuDao

class RecentOrderMenuRepository(private val dao: RecentOrderMenuDao) {

    suspend fun getAllRecentOrderMenu() = dao.getAllRecentOrderMenu()
    suspend fun getAllRecentOrderMenu_OrderBy_date() = dao.getAllRecentOrderMenu_OrderBy_date()
}