package tech.mohamedashraf.antiscamagent

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import tech.mohamedashraf.data.firebase.*
import tech.mohamedashraf.data.muse.MuseAPI
import tech.mohamedashraf.data.repository.*
import tech.mohamedashraf.domain.repository.*
import tech.mohamedashraf.domain.usecase.*
import tech.mohamedashraf.presentation.agent.AgentViewModel
import tech.mohamedashraf.presentation.chat.ChatViewModel
import tech.mohamedashraf.presentation.details.ProductDetailsViewModel
import tech.mohamedashraf.presentation.home.HomeViewModel
import tech.mohamedashraf.presentation.login.LoginViewModel
import tech.mohamedashraf.presentation.register.RegisterViewModel
import tech.mohamedashraf.presentation.splash.SplashViewModel
import tech.mohamedashraf.presentation.verification.VerificationViewModel

val appModule = module {

    viewModelOf(::SplashViewModel)

    viewModel { LoginViewModel(get()) }

    viewModel { RegisterViewModel(get()) }

    viewModel { VerificationViewModel(get(), get(), get()) }

    viewModel { HomeViewModel(get(), get()) }

    viewModel { ProductDetailsViewModel(get()) }

    viewModel { ChatViewModel(get()) }

    viewModel { AgentViewModel(get()) }
}

val dataModule = module {

    single { Authentication() }

    single { FireStoreDB() }

    single { MuseAPI() }

    singleOf(::UserRepositoryImplementation).bind(UserRepository::class)

    single<MarketplaceRepository> { MarketplaceRepositoryImplementation(get(), get()) }
}

val domainModule = module {

    single { CheckAuthenticatedUser(get()) }

    single { Login(get()) }

    single { Register(get()) }

    single { SendVerificationEmail(get()) }

    single { CheckUserVerification(get()) }

    single { Logout(get()) }

    single { GetMarketplaceProducts(get(), get()) }

    single { GetProductDetails(get()) }

    single { StartAgenticDealVerification(get()) }
}