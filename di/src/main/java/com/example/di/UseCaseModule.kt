package com.example.di

import com.example.domain.usecase.GetCharacterByIdUseCase
import com.example.domain.usecase.GetCharacterByIdUseCaseImpl
import com.example.domain.usecase.GetCharactersListUseCase
import com.example.domain.usecase.GetCharactersListUseCaseImpl
import org.koin.dsl.module

object UseCaseModule {
    val useCaseModule = module {
        factory<GetCharactersListUseCase> { GetCharactersListUseCaseImpl(get(), get()) }
        factory<GetCharacterByIdUseCase> { GetCharacterByIdUseCaseImpl(get(), get()) }
    }
}
