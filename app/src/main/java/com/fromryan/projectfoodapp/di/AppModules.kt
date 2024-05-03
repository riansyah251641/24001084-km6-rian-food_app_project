package com.fromryan.projectfoodapp.di

import android.content.SharedPreferences
import com.fromryan.projectfoodapp.data.datasource.auth.AuthDataSource
import com.fromryan.projectfoodapp.data.datasource.auth.FirebaseAuthDataSource
import com.fromryan.projectfoodapp.data.datasource.cart.CartDataSource
import com.fromryan.projectfoodapp.data.datasource.cart.CartDatabaseDataSource
import com.fromryan.projectfoodapp.data.datasource.catalog.CatalogApiDataSource
import com.fromryan.projectfoodapp.data.datasource.catalog.CatalogDataSource
import com.fromryan.projectfoodapp.data.datasource.category.CategoryApiDataSource
import com.fromryan.projectfoodapp.data.datasource.category.CategoryDataSource
import com.fromryan.projectfoodapp.data.repository.CartRepository
import com.fromryan.projectfoodapp.data.repository.CartRepositoryImpl
import com.fromryan.projectfoodapp.data.repository.CatalogRepository
import com.fromryan.projectfoodapp.data.repository.CatalogRepositoryImpl
import com.fromryan.projectfoodapp.data.repository.CategoryRepository
import com.fromryan.projectfoodapp.data.repository.CategoryRepositoryImpl
import com.fromryan.projectfoodapp.data.repository.UserRepository
import com.fromryan.projectfoodapp.data.repository.UserRepositoryImpl
import com.fromryan.projectfoodapp.data.source.firebase.FirebaseService
import com.fromryan.projectfoodapp.data.source.firebase.FirebaseServiceImpl
import com.fromryan.projectfoodapp.data.source.lokal.database.AppDatabase
import com.fromryan.projectfoodapp.data.source.lokal.database.dao.CartDao
import com.fromryan.projectfoodapp.data.source.network.services.ApiDataServices
import com.fromryan.projectfoodapp.presentation.cart.CardViewModel
import com.fromryan.projectfoodapp.presentation.checkout.CheckoutViewModel
import com.fromryan.projectfoodapp.presentation.detailfood.DetailFoodViewModel
import com.fromryan.projectfoodapp.presentation.home.HomeViewModel
import com.fromryan.projectfoodapp.presentation.login.LoginViewModel
import com.fromryan.projectfoodapp.presentation.profile.ProfileViewModel
import com.fromryan.projectfoodapp.presentation.register.RegisterViewModel
import com.fromryan.projectfoodapp.utils.SharedPreferenceUtils
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    private val networkModule =
        module {
            single<ApiDataServices> { ApiDataServices.invoke() }

        }

    private val localModule =
        module {
            single<AppDatabase> { AppDatabase.getInstance(androidContext()) }
            single<CartDao> { get<AppDatabase>().cartDao() }
        }

    private val dataSourceModule =
        module {
            single<CartDataSource> { CartDatabaseDataSource(get()) }
            single<CategoryDataSource> { CategoryApiDataSource(get()) }
            single<CatalogDataSource> { CatalogApiDataSource(get()) }
            single<AuthDataSource> { FirebaseAuthDataSource(get()) }
        }

    private val repositoryModule =
        module {
            single<CartRepository> { CartRepositoryImpl(get()) }
            single<CategoryRepository> { CategoryRepositoryImpl(get()) }
            single<CatalogRepository> { CatalogRepositoryImpl(get()) }
            single<UserRepository> { UserRepositoryImpl(get()) }
        }

    private val firebaseModule =
        module {
            single<FirebaseService> { FirebaseServiceImpl() }
            single<FirebaseAuth> { FirebaseAuth.getInstance() }
        }

    private val viewModelModule =
        module {

            viewModel { LoginViewModel(get()) }
            viewModel { RegisterViewModel(get()) }
            viewModel { HomeViewModel(get(), get(), get()) }
            viewModel { CardViewModel(get(),get()) }
            viewModel { ProfileViewModel(get()) }
            viewModel { CheckoutViewModel(get()) }

            viewModel { params ->
                DetailFoodViewModel(
                    extras = params.get(),
                    cartRepository = get(),
                )
            }
        }
    val modules =
        listOf<Module>(
            networkModule,
            localModule,
            firebaseModule,
            dataSourceModule,
            repositoryModule,
            viewModelModule,
        )
}