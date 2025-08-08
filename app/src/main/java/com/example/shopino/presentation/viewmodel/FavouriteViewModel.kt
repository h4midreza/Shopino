package com.example.shopino.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopino.domain.model.ItemsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(

) : ViewModel() {

    private val _favouriteItems = MutableStateFlow<List<ItemsModel>>(emptyList())
    val favouriteItems: StateFlow<List<ItemsModel>> = _favouriteItems.asStateFlow()

    fun addFavourite(item: ItemsModel) {
        viewModelScope.launch {
            _favouriteItems.update { currentList ->
                if (currentList.none { it.id == item.id }) {

                    currentList + item.copy(isFavorite = true)
                } else {
                    currentList
                }
            }
        }
    }

    fun removeFavourite(item: ItemsModel) {
        viewModelScope.launch {
            _favouriteItems.update { currentList ->
                currentList.filterNot { it.id == item.id }
            }
        }
    }

    fun isItemFavourite(itemId: Int): Boolean {
        return _favouriteItems.value.any { it.id == itemId }
    }
}
