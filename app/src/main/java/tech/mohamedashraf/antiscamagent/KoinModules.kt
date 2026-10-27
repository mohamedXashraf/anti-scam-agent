package tech.mohamedashraf.antiscamagent

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import tech.mohamedashraf.antiscamagent.domain.usecase.*
import tech.mohamedashraf.antiscamagent.data.repository.*
import tech.mohamedashraf.antiscamagent.domain.repository.*
import tech.mohamedashraf.antiscamagent.presentation.home.HomeViewModel
import tech.mohamedashraf.antiscamagent.presentation.newsale.NewSaleViewModel
import tech.mohamedashraf.antiscamagent.presentation.splash.SplashViewModel

val appModule = module {

    viewModelOf(::SplashViewModel)

    viewModel { HomeViewModel(get(), get()) }

    viewModel { NewSaleViewModel(get()) }
}

val dataModule = module {

    single { Database(AndroidSqliteDriver(Database.Schema, get(), "netsold.db")) }

    singleOf(::UserRepositoryImplementation).bind(UserRepository::class)

    single<SaleRepository> { SaleRepositoryImplementation(get()) }
}

val domainModule = module {

    single { CheckUser(get()) }

    single { AddNewSale(get(), get()) }

    single { GetMonthAnalysis(get(), get()) }

    single { DownloadMonthSalesCSV() }
}