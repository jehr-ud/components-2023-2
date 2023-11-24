package com.example.fruits.repositories

import com.example.fruits.models.Fruit
import com.example.fruits.network.FruitsApi
import com.example.fruits.network.RetrofitClient

class FruitsRepository {
    private val fruitsApi: FruitsApi = RetrofitClient.fruitsApi

    suspend fun getFruits(): List<Fruit> {
        return fruitsApi.getAllFruits()
    }
}