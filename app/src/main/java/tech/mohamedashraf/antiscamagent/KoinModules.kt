package tech.mohamedashraf.antiscamagent

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import tech.mohamedashraf.data.firebase.*
import tech.mohamedashraf.data.repository.*
import tech.mohamedashraf.domain.repository.*
import tech.mohamedashraf.domain.usecase.*
import tech.mohamedashraf.presentation.login.LoginViewModel
import tech.mohamedashraf.presentation.register.RegisterViewModel
import tech.mohamedashraf.presentation.splash.SplashViewModel

val appModule = module {

    viewModelOf(::SplashViewModel)

    viewModel { LoginViewModel(get()) }

    viewModel { RegisterViewModel(get()) }
}

val dataModule = module {

    single { Authentication() }

    single { FireStoreDB() }

    singleOf(::UserRepositoryImplementation).bind(UserRepository::class)

    single<MarketplaceRepository> { MarketplaceRepositoryImplementation(get(), get()) }
}

val domainModule = module {

    single { CheckAuthenticatedUser(get()) }

    single { Login(get()) }

    single { Register(get()) }
}