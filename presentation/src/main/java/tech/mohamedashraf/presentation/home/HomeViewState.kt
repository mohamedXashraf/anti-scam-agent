package tech.mohamedashraf.presentation.home

import tech.mohamedashraf.domain.rules.Product

sealed class HomeViewState {
    data object ViewInitialization : HomeViewState()
    data object Loading : HomeViewState()
    data class Data(val products: List<Product>) : HomeViewState()
    data object LoggedOut : HomeViewState()
    data class Error(val error: Throwable) : HomeViewState()
}