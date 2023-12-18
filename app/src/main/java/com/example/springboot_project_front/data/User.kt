package com.example.springboot_project_front.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    val email: String,
    val password: String
) : Serializable

data class AuthResponse(@SerializedName("token") val token: String) : Serializable