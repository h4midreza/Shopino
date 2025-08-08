package com.example.shopino.data.model

import com.google.gson.annotations.SerializedName

data class ProductDTO(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String?,

    @SerializedName("price")
    val price: Double?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("category")
    val category: String?,

    @SerializedName("image")
    val image: String?,

    @SerializedName("rating")
    val rating: RatingDTO?
)

data class RatingDTO(
    @SerializedName("rate")
    val rate: Double?,

    @SerializedName("count")
    val count: Int?
)

