package tech.mohamedashraf.presentation.details

sealed class ProductDetailsViewIntent {
    data class GetProductDetails(val productId: String) : ProductDetailsViewIntent()
}