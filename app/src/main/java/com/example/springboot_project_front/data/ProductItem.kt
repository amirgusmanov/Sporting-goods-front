package com.example.springboot_project_front.data

import com.example.springboot_project_front.base.MapTo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductItem(
    val id: Long = 0,
    val name: String,
    val price: Double,
    val description: String,
    val productQuantity: Int
) : MapTo<ProductRequest, ProductItem> {
    override fun mapTo(): ProductRequest = ProductRequest(
        id = this.id,
        name = this.name,
        price = this.price,
        description = this.description,
        productQuantity = this.productQuantity
    )
}

data class ProductRequest(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double,
    @SerializedName("description") val description: String,
    @SerializedName("productQuantity") val productQuantity: Int
) : Serializable