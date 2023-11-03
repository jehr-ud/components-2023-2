package com.ud.listuserfront.models

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id")
    val id: String?,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("nombre")
    val name: String
)