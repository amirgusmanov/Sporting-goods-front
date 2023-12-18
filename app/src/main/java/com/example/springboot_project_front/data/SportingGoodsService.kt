package com.example.springboot_project_front.data

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SportingGoodsService {

    @POST("/api/auth/register")
    suspend fun register(@Body user: User): String?

    @POST("/api/auth/login")
    suspend fun login(@Body user: User): String?

    @GET("/api/sporting-goods")
    suspend fun getAllSportingGoods(): List<ProductItem>

    @POST("/api/sporting-goods")
    suspend fun addSportingGood(@Body productDTO: ProductRequest): Boolean

    @PUT("/api/sporting-goods/{id}")
    suspend fun updateSportingGood(
        @Path("id") id: Long,
        @Query("count") count: Int
    ): Boolean

    @DELETE("/api/sporting-goods/{id}")
    suspend fun deleteSportingGood(@Path("id") id: Long): Boolean
}