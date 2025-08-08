package com.example.shopino.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopino.domain.model.CategoryModel // مدل جدید CategoryModel
import com.example.shopino.domain.model.ItemsModel
import com.example.shopino.domain.model.SliderModel
import com.example.shopino.domain.usecase.category.GetCategoriesUseCase // UseCase جدید
import com.example.shopino.domain.usecase.product.GetProductByCategoryUseCase
import com.example.shopino.domain.usecase.product.GetProductsUseCase
import com.example.shopino.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ProductListUiState(
    val isLoading: Boolean = false,
    val items: List<ItemsModel> = emptyList(),
    val error: String? = null
)

data class CategoryListUiState(
    val isLoading: Boolean = false,
    val items: List<CategoryModel> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductsByCategoryUseCase: GetProductByCategoryUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _productListState = MutableStateFlow(ProductListUiState())
    val productListState: StateFlow<ProductListUiState> = _productListState.asStateFlow()

    private val _filteredProductListState = MutableStateFlow(ProductListUiState())
    val filteredProductListState: StateFlow<ProductListUiState> = _filteredProductListState.asStateFlow()

    private val _categoryListState = MutableStateFlow(CategoryListUiState())
    val categoryListState: StateFlow<CategoryListUiState> = _categoryListState.asStateFlow()

    private val _banners = MutableStateFlow<List<SliderModel>>(emptyList())
    val banners: StateFlow<List<SliderModel>> = _banners.asStateFlow()

    init {
        fetchProducts()
        fetchCategories()
        loadBannersFromMock()
    }

    fun fetchProducts() {
        getProductsUseCase().onEach { result ->
            _productListState.update {
                when (result) {
                    is Result.Loading -> it.copy(isLoading = true, error = null)
                    is Result.Success -> it.copy(isLoading = false, items = result.data, error = null)
                    is Result.Error -> it.copy(isLoading = false, error = result.message ?: "Unknown product error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun fetchProductsByCategory(categoryId: String) {
        getProductsByCategoryUseCase(categoryId).onEach { result ->
            _filteredProductListState.update {
                when (result) {
                    is Result.Loading -> it.copy(isLoading = true, error = null)
                    is Result.Success -> it.copy(isLoading = false, items = result.data, error = null)
                    is Result.Error -> it.copy(isLoading = false, error = result.message ?: "Unknown category product error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun fetchCategories() {
        getCategoriesUseCase().onEach { result ->
            _categoryListState.update {
                when (result) {
                    is Result.Loading -> it.copy(isLoading = true, error = null)
                    is Result.Success -> it.copy(isLoading = false, items = result.data, error = null)
                    is Result.Error -> it.copy(isLoading = false, error = result.message ?: "Unknown category fetch error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadBannersFromMock() {
        val testBanners = listOf(
            SliderModel(com.example.shopino.R.drawable.banner1),
            SliderModel(com.example.shopino.R.drawable.banner2),
            SliderModel(com.example.shopino.R.drawable.banner3),
            SliderModel(com.example.shopino.R.drawable.banner4),
            SliderModel(com.example.shopino.R.drawable.banner5),
            SliderModel(com.example.shopino.R.drawable.banner6),
            SliderModel(com.example.shopino.R.drawable.banner7),
        )
        _banners.value = testBanners
    }
}