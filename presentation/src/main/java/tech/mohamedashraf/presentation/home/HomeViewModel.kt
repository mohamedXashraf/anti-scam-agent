package tech.mohamedashraf.presentation.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.mohamedashraf.domain.usecase.GetMarketplaceProducts
import tech.mohamedashraf.presentation.application.BaseViewModel

class HomeViewModel(private val getProducts: GetMarketplaceProducts) : BaseViewModel<HomeViewState, HomeViewIntent>() {

    init {
        observeIntents {
            when (it) {
                is HomeViewIntent.GetMarketplaceProducts -> getMarketplaceProducts()
            }
        }
    }

    private fun getMarketplaceProducts() = viewModelScope.launch(Dispatchers.IO) {
        try {
            state.postValue(HomeViewState.Loading)
            state.postValue(HomeViewState.Data(getProducts()))
        } catch (error: Exception) {
            state.postValue(HomeViewState.Error(error))
        }
    }
}