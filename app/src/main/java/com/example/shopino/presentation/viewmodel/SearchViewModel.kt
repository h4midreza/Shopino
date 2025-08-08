package com.example.shopino.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopino.domain.model.ItemsModel
import com.example.shopino.domain.repository.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<ItemsModel>>(emptyList())
    val searchResults: StateFlow<List<ItemsModel>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        _searchQuery
            .debounce(500)
            .onEach { query ->
                if (query.length > 2) {
                    performSearch(query)
                } else {
                    _searchResults.value = emptyList()
                    _errorMessage.value = null
                }
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    private fun performSearch(query: String) {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            searchProductsUseCase(query)
                .catch { e ->
                    _errorMessage.value = "Error: ${e.localizedMessage ?: "An unexpected error occurred"}"
                    _searchResults.value = emptyList()
                }
                .collect { products ->
                    _searchResults.value = products
                    if (products.isEmpty() && query.isNotBlank()) {
                    }
                }
            _isLoading.value = false
        }
    }
}
