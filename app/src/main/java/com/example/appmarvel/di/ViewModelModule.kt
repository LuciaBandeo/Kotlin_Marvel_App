package com.example.appmarvel.di

import com.example.appmarvel.mvvm.viewmodel.CharacterFragmentViewModel
import com.example.appmarvel.mvvm.viewmodel.CharactersListViewModel
import com.example.appmarvel.mvvm.viewmodel.MainViewModel
import com.example.appmarvel.mvvm.viewmodel.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val viewModelModule = module {
        viewModel { SplashScreenViewModel() }
        viewModel { MainViewModel() }
        viewModel { CharactersListViewModel(get()) }
        viewModel { CharacterFragmentViewModel(get()) }
    }
}
