package com.example.shopino.data.mapper

import com.example.shopino.data.model.ProductDTO
import com.example.shopino.domain.model.ItemsModel

fun ProductDTO.toDomainModel(): ItemsModel {
    var correctedImageUrl = this.image ?: ""
    if (correctedImageUrl.endsWith("_.jpg")) {
        correctedImageUrl = correctedImageUrl.replace("_.jpg", "_t.png")
    }
    return ItemsModel(
        id = this.id,
        title = this.title ?: "Unknown Product",
        description = this.description ?: "",
        imageUrl = correctedImageUrl,
        modelsAvailable = ArrayList(),
        price = this.price ?: 0.0,
        ratingRate = this.rating?.rate ?: 0.0,
        ratingCount = this.rating?.count ?: 0,
        numberInCart = 0,
        showRecommended = false,
        category = this.category ?: "Uncategorized"
    )
}

fun List<ProductDTO>.toDomainModelList(): List<ItemsModel> {
    return this.map { it.toDomainModel() }
}
