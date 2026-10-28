package tech.mohamedashraf.presentation.details

import tech.mohamedashraf.domain.rules.Product

sealed class ProductDetailsViewState {
    data object ViewInitialization : ProductDetailsViewState()
    data object Loading : ProductDetailsViewState()
    data class Data(val product: Product) : ProductDetailsViewState()
    data object InvalidProduct : ProductDetailsViewState()
    data class Error(val error: Throwable) : ProductDetailsViewState()
}