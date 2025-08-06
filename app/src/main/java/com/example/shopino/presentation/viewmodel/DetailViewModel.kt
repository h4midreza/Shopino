package com.example.shopino.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopino.domain.model.ItemsModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val gson: Gson
) : ViewModel() {

    companion object {
        const val ITEM_MODEL_KEY = "item_key"
    }

    private val _item = MutableStateFlow<ItemsModel?>(null)
    val item: StateFlow<ItemsModel?> = _item.asStateFlow()

    init {
        viewModelScope.launch {
            val itemJsonString = savedStateHandle.get<String>(ITEM_MODEL_KEY)
            if (itemJsonString != null) {
                try {
                    val deserializedItem = gson.fromJson(itemJsonString, ItemsModel::class.java)
                    _item.value = deserializedItem
                } catch (e: Exception) {
                    _item.value = null
                }
            }
        }
    }
}
