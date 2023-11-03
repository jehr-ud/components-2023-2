package com.ud.listusersapp.network

import com.ud.listuserfront.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("mostrarUsuarios")
    fun getUsers(): Call<List<User>>

    @POST("nuevoUsuario")
    fun createUser(@Body user: User): Call<String>

    @GET("buscarUsuarioPorId/{id}")
    fun getUserById(@Path("id") userId: String): Call<User>

    @GET("borrarUsuario/{id}")
    fun deleteUserById(@Path("id") userId: String?): Call<String>
}
