package com.example.fruits.network

import com.example.fruits.models.Fruit
import retrofit2.http.GET

interface FruitsApi {
    @GET("fruit/all")
    suspend fun getAllFruits(): List<Fruit>
}