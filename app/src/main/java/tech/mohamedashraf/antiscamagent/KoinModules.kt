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

val appModule = module {

//    viewModelOf(::SplashViewModel)

//    viewModel { HomeViewModel(get(), get()) }
}

val dataModule = module {

    single { Authentication() }

    single { FireStoreDB() }

    singleOf(::UserRepositoryImplementation).bind(UserRepository::class)

//    single<SaleRepository> { SaleRepositoryImplementation(get()) }
}

val domainModule = module {

    single { Login(get()) }
}