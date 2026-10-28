package tech.mohamedashraf.presentation.details

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.mohamedashraf.domain.usecase.GetProductDetails
import tech.mohamedashraf.presentation.application.BaseViewModel

class ProductDetailsViewModel(private val getProductDetails: GetProductDetails) : BaseViewModel<ProductDetailsViewState, ProductDetailsViewIntent>() {

    init {
        observeIntents {
            when (it) {
                is ProductDetailsViewIntent.GetProductDetails -> getProduct(it.productId)
            }
        }
    }

    private fun getProduct(productId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            state.postValue(ProductDetailsViewState.Loading)
            val product = getProductDetails(productId)
            if (product != null) state.postValue(ProductDetailsViewState.Data(product))
            else state.postValue(ProductDetailsViewState.InvalidProduct)
        } catch (error: Exception) {
            state.postValue(ProductDetailsViewState.Error(error))
        }
    }
}