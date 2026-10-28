package tech.mohamedashraf.presentation.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.mohamedashraf.domain.usecase.GetMarketplaceProducts
import tech.mohamedashraf.domain.usecase.Logout
import tech.mohamedashraf.presentation.application.BaseViewModel

class HomeViewModel(
    private val getProducts: GetMarketplaceProducts,
    private val logout: Logout
) : BaseViewModel<HomeViewState, HomeViewIntent>() {

    init {
        observeIntents {
            when (it) {
                is HomeViewIntent.GetMarketplaceProducts -> getMarketplaceProducts()
                is HomeViewIntent.Logout -> exit()
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

    private fun exit() = viewModelScope.launch(Dispatchers.IO) {
        try {
            state.postValue(HomeViewState.Loading)
            logout()
            state.postValue(HomeViewState.LoggedOut)
        } catch (error: Exception) {
            state.postValue(HomeViewState.Error(error))
        }
    }
}