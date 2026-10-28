package tech.mohamedashraf.presentation.home

sealed class HomeViewIntent {
    data object GetMarketplaceProducts : HomeViewIntent()
    data object Logout : HomeViewIntent()
}