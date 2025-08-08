package com.example.shopino.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(
    val apiName: String,
    val displayName: String,
    val iconResId: Int
) : Parcelable
