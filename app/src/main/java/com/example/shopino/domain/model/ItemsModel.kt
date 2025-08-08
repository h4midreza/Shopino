package com.example.shopino.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemsModel(
    val id: Int,
    var title: String = "",
    var description: String = "",
    var imageUrl: String = "",
    var modelsAvailable: ArrayList<String> = ArrayList(),
    var price: Double = 0.0,
    var ratingRate: Double = 0.0,
    var ratingCount: Int = 0,
    var numberInCart: Int = 0,
    var showRecommended: Boolean = false,
    var category: String = "",
    val isFavorite: Boolean = false
) : Parcelable