package com.olar.di.module


import com.olar.ui.home.HomeViewModel
import com.olar.ui.auth.AuthViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get(),get()) }
    viewModel { HomeViewModel(get(),get()) }

}
