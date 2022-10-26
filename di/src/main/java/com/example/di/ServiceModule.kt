package com.example.di

import com.example.data.service.MarvelServiceImpl
import com.example.domain.service.MarvelService
import org.koin.dsl.module

object ServiceModule {
    val serviceModule = module {
        factory<MarvelService> { MarvelServiceImpl(get()) }
    }
}
