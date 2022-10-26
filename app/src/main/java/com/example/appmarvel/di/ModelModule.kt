package com.example.appmarvel.di

import com.example.appmarvel.mvvm.model.CharacterFragmentModel
import com.example.appmarvel.mvvm.model.CharacterListModel
import org.koin.dsl.module

object ModelModule {
    val modelModule = module {
        factory { CharacterListModel(get()) }
        factory { CharacterFragmentModel(get()) }
    }
}
